package com.esiee.sudoku;

import java.util.Random;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Lib de methodes static contenant les algo
 * @author JM
 *
 */
public class lib {
	
	/**
	 * Vérifie le gain d'une grille
	 * @param p
	 * @return
	 */
	public static boolean gain(final Grille p) {
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				if(!estOK(p,p.getValeurCase(i,j),i,j)) return false;
			}
		}
		return true;
	}
	
	public static Grille effacerGrille(final Grille p) {
		Grille p_resultat = new Grille();
		copieGrille(p_resultat,p);
		p_resultat.brouillonsFalse();
		for(int i=0;i<9;i++) 
			for(int j=0;j<9;j++)
				if(!p_resultat.getCase(i,j).isProtected())
					p_resultat.setValeurCase(i,j,0);
		return p_resultat;
	}
	
	
	public static Grille remplirGrille(final Grille p, final int val) {
		Grille p_resultat = new Grille();
		copieGrille(p_resultat,p);
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				p_resultat.setValeurCase(i,j,val);
			}
		}
		return p_resultat;
	}
	
	public static void copieGrille(Grille i1, Grille i2) {
		for(int i=0;i<9;i++) 
			for(int j=0;j<9;j++) {
				i1.setValeurCase(i,j,i2.getValeurCase(i,j));
				i1.getCase(i,j).setBrouillon(i2.getCase(i,j).getBrouillon());
				if(i2.getCase(i,j).isProtected())
					i1.getCase(i,j).setProtected(true);
			}
	}
	
	/**
	 * Afin de générer plus rapidement la grille, des chiffres aléatoires (non bloquant) sont placés
	 * @param p
	 * @return
	 */
	public static Grille metNombresAleatoires(final Grille p) {
		Grille p_resultat = new Grille();
		copieGrille(p_resultat,p);
		p_resultat.setValeurCase(0,0,random(1,9));
		p_resultat.setValeurCase(3,1,random(1,9));
		p_resultat.setValeurCase(6,2,random(1,9));
		p_resultat.setValeurCase(1,3,random(1,9));
		p_resultat.setValeurCase(4,4,random(1,9));
		p_resultat.setValeurCase(7,5,random(1,9));
		p_resultat.setValeurCase(2,6,random(1,9));
		p_resultat.setValeurCase(5,7,random(1,9));
		p_resultat.setValeurCase(8,8,random(1,9));
		return p_resultat;
	}
	
	public static boolean estVide(final Grille p) {
		for(int i=1;i<10;i++)
			if(estPresent(p,i))
				return false;
		return true;
	}
	
	private static ExecuteGenereGrille task;
	public static void genereGrilleTask() {
		MainActivity.activity.getView().get_bouton_new_().block = true;
		task = new ExecuteGenereGrille();
		task.execute();
	}
	
	/**
	 * AsyncTask qui genere une grille
	 * @author JM
	 *
	 */
	static class ExecuteGenereGrille extends AsyncTask<Void, Void, Grille>{
		@Override
		protected Grille doInBackground(Void... arg0) {
			return genereGrille();
		}

		@Override
		protected void onCancelled() {
			MainActivity.activity.getView().get_bouton_new_().deblock();
			super.onCancelled();
		}

		@Override
		protected void onPostExecute(Grille result) {
			MainActivity.activity.getView().setGrille(result);
			MainActivity.activity.getView().get_bouton_new_().deblock();
			super.onPostExecute(result);
		}
	}
	
	/**
	 * Methode qui genere une grille
	 * @return
	 */
	public static Grille genereGrille() {
		Grille p_resultat = new Grille();
		p_resultat = metNombresAleatoires(p_resultat);
		p_resultat.brouillonsTrue();
		p_resultat = resoudreGrille(p_resultat);
		if(p_resultat==null) {
			return genereGrille();
		}
		
		while(nbCaseRemplie(p_resultat)>30) {
			int indiceNombre = random(1,nbCaseRemplie(p_resultat));
			int indiceCourant = 0;
			
			label:for(int i=0;i<9;i++) 
				for(int j=0;j<9;j++) {
					if(p_resultat.getValeurCase(i,j)!=0) {
						indiceCourant++;
						if(indiceCourant == indiceNombre) {
							p_resultat.setValeurCase(i,j,0);
							break label;
						}
					}
				}
		}
		p_resultat=metProtected(p_resultat);
		return p_resultat;
	}
	
	public static Grille metProtected(final Grille p) {
		Grille p_resultat = new Grille();
		lib.copieGrille(p_resultat,p);
		for(int i=0;i<9;i++) 
			for(int j=0;j<9;j++)
				if(p_resultat.getValeurCase(i,j)!=0)
					p_resultat.getCase(i,j).setProtected(true);
		return p_resultat;
	}
	
	public static int nbCaseRemplie(final Grille p) {
		int res = 0;
		for(int i=0;i<9;i++) 
			for(int j=0;j<9;j++)
				if(p.getValeurCase(i,j)!=0)
					res++;
		return res;
	}
	
	/**
	 * Alorithme de resolution de sudoku
	 * @param actuelle
	 * @return
	 */
	public static Grille resoudreGrille(Grille actuelle) {
		if(!estPresent(actuelle,0)){return actuelle;}
		
		/* Enleve les possibilités des cases non vides */
		for(int i=0;i<9;i++) 
			for(int j=0;j<9;j++) {
				if(actuelle.getValeurCase(i,j)!=0) {
					
					int tmp = actuelle.getValeurCase(i,j);
					actuelle.setValeurCase(i,j,0);
					if(!estOK(actuelle,tmp,i,j)) {Log.d("lib","On retourne une grille nulle, valeurs incompatibles à l'entrée: i="+i+" j="+j+" valeur="+tmp);return null;}
					actuelle.setValeurCase(i,j,tmp);
					
					actuelle.getCase(i,j).zeroBrouillon();
					for(int u=0;u<9;u++) {
						actuelle.getCase(i,u).setBrouillon(actuelle.getValeurCase(i,j),false);
						actuelle.getCase(u,j).setBrouillon(actuelle.getValeurCase(i,j),false);
						actuelle.getCase((i/3)*3+u%3,(j/3)*3+u/3).setBrouillon(actuelle.getValeurCase(i,j),false);
					}
				}
			}
		int min = 100; int i_=-1; int j_=-1; int stop = 0;
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				
				/* S'il n'y a qu'une possibilité, on ajoute */
				if(actuelle.getNombreBrouillons(i,j)==1) {
					actuelle.setValeurCase(i,j,actuelle.getPremierBrouillon(i,j));
					actuelle.getCase(i,j).zeroBrouillon();
					for(int u=0;u<9;u++) {
						actuelle.getCase(i,u).setBrouillon(actuelle.getValeurCase(i,j),false);
						actuelle.getCase(u,j).setBrouillon(actuelle.getValeurCase(i,j),false);
						actuelle.getCase((i/3)*3+u%3,(j/3)*3+u/3).setBrouillon(actuelle.getValeurCase(i,j),false);
					}
				}
			
				if(actuelle.getValeurCase(i,j)==0) {
					/* Si aucune possibilité, on recommence */
					if(actuelle.getNombreBrouillons(i,j)==0) {
						return null;
					}
					/* On mémorise la case qui a le moins de possibilités */
					if(actuelle.getNombreBrouillons(i,j)<min) {
						min=actuelle.getNombreBrouillons(i,j);
						i_=i; j_=j;
					}
				}
				
				if(actuelle.getNombreBrouillons(i,j)==0) stop++;
			}
		}	
			
		/* On joue la case qui a le moins de possibilités */
		if(i_!=-1 && j_!=-1)	
			for(int k=1;k<=9;k++) {
				if(estOK(actuelle,k,i_,j_)) {
					Grille g = new Grille();
					copieGrille(g,actuelle);
					g.setValeurCase(i_,j_,k);
					g.getCase(i_,j_).zeroBrouillon();
					for(int u=0;u<9;u++) {
						g.getCase(i_,u).setBrouillon(g.getValeurCase(i_,j_),false);
						g.getCase(u,j_).setBrouillon(g.getValeurCase(i_,j_),false);
						g.getCase((i_/3)*3+u%3,(j_/3)*3+u/3).setBrouillon(g.getValeurCase(i_,j_),false);
					}
					Grille res = resoudreGrille(g);
					if(res!=null){return res;}
				}
			}
		
		if(stop==81)
			return actuelle;
		return null;
	}
	
	public static boolean estPresent(Grille p, int valeur) {
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				if(p.getValeurCase(i,j)==valeur) return true;
			}
		}
		return false;
	} 
	
	public static boolean estOK(final Grille p, final int valeur, final int x, final int y) {
		if(estDansLigne(p,valeur,y,x)) return false;
		if(estDansColonne(p,valeur,x,y)) return false;
		if(estDansGdCarre(p,valeur,x/3,y/3,x,y)) return false;
		return true;
	}
	
	public static boolean estOK(final Grille p, final int x, final int y) {
		for(int i =1; i<=9; i++)
		{
			if(estDansLigne(p,i,y)) return false;
			if(estDansColonne(p,i,x)) return false;
			if(estDansGdCarre(p,i,x/3,y/3)) return false;
		}
		return true;
	}
	
	public static boolean estDansLigne(final Grille p, final int valeur, final int ligne) {
		for(int i=0;i<9;i++)
			if(p.getValeurCase(i,ligne)==valeur) 
				return true;
		return false;
	}
	
	public static boolean estDansLigne(final Grille p, final int valeur, final int ligne, final int colonne) {
		for(int i=0;i<9;i++)
			if(p.getValeurCase(i,ligne)==valeur&&i!=colonne) 
				return true;
		return false;
	}
	
	public static boolean estDansColonne(final Grille p, final int valeur, final int colonne) {
		for(int i=0;i<9;i++)
			if(p.getValeurCase(colonne,i)==valeur) 
				return true;
		return false;
	}
	
	public static boolean estDansColonne(final Grille p, final int valeur, final int colonne, final int ligne) {
		for(int i=0;i<9;i++)
			if(p.getValeurCase(colonne,i)==valeur&& i!=ligne) 
				return true;
		return false;
	}
	
	public static boolean estDansGdCarre(final Grille p, final int valeur, final int GdCarreX, final int GDCarreY) {
		for(int i=0;i<9;i++) 
			for(int j=0;j<9;j++) 
				if( GdCarreX*3<=i && i<(GdCarreX+1)*3    &&    GDCarreY*3<=j && j<(GDCarreY+1)*3 )
					if(p.getValeurCase(i,j)==valeur)
						return true;
		return false;
	}
	
	public static boolean estDansGdCarre(final Grille p, final int valeur, final int GdCarreX, final int GDCarreY, final int pX, final int pY) {
		for(int i=0;i<9;i++) 
			for(int j=0;j<9;j++) 
				if( GdCarreX*3<=i && i<(GdCarreX+1)*3    &&    GDCarreY*3<=j && j<(GDCarreY+1)*3 && !(i==pX && j==pY))
					if(p.getValeurCase(i,j)==valeur)
						return true;
		return false;
	}
	
	public static int random(final int min, final int max) {
		Random r = new Random();
		return r.nextInt(max - min + 1) + min;
	}
	
	public static int min(final int i,final int j) {
		if(i>j) return j;
		return i;
	}
	public static int max(final int i,final int j) {
		if(i<j) return j;
		return i;
	}
	public static int abs(final int i) {
		if(i<0) return -i;
		return i;
	}
	
	public static int parseInt(String number, int defaultVal) {
		try {
			return Integer.parseInt(number);
		} catch (NumberFormatException e) {
			return defaultVal;
		}
	}
	
	/**
	 * Methode de sauvegarde de la grille
	 */
	public static void sauvegarde() {
		String res ="";
		
		for(int i=0; i<9;i++)
			for(int j=0; j<9;j++)
				res += MainActivity.activity.getView().getGrille().getValeurCase(i,j)+",";
		
		res += "#";
		
		for(int i=0; i<9;i++)
			for(int j=0; j<9;j++) {
				if(MainActivity.activity.getView().getGrille().getCase(i,j).isProtected())
					res+="P,";
				else
					res+="O,";
			}
		
		/* Ecriture dans un fichier */
		lib_TXT.ecrire_droid(res);
	}
	
	/**
	 * Methode de chargement de la grille
	 */
	public static void charger() {
		/* Lecture dans un fichier */
		String res = lib_TXT.lire_droid();
		
		if(res==null) return;
		String [] lecture=res.split("#");
		
		if(lecture.length==1) return;
		String [] lectureVal=lecture[0].split(",");
		String [] lectureProt=lecture[1].split(",");
		
		for(int i=0; i<9;i++)
			for(int j=0; j<9;j++) {
				int indice = j%9+i*9;
				if(indice<lectureVal.length)
					MainActivity.activity.getView().getGrille().setValeurCase(i,j,parseInt(lectureVal[indice],0));
				if(indice<lectureProt.length)
					if(lectureProt[indice].equals("P"))
						MainActivity.activity.getView().getGrille().getCase(i,j).setProtected(true);
			}
	}
}
