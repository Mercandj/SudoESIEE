package com.esiee.sudoku;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * 
 * Saisie en cercle
 * 
 * @author JM
 *
 */
public class Cercle_Saisie {

	private int x_,y_,r_,r_int_;
	private int x_case_=-1;
	private int y_case_=-1;
	private int couleur_;
	private int cote_;
	private boolean plein_,demi_droite_,demi_gauche_,demi_bas_,demi_haut_;
	
	public Cercle_Saisie (int x, int y, int r, int couleur) {
		this.x_=x;
		this.y_=y;
		this.r_=r;
		this.r_int_=5*r_/12;
		this.couleur_=couleur;
		
		cote_ = lib.min(MainView.largeurPixel,MainView.hauteurPixel)/9;
		
		if (MainView.largeurPixel < MainView.hauteurPixel) { // Portrait
			int decalageY = (MainView.hauteurPixel-MainView.largeurPixel)/2;
			for(int i=0;i<10;i++)
				for(int j=0;j<10;j++) {
					if(i*cote_<x && x<(i+1)*cote_) x_case_=i;
					if(decalageY+j*cote_<y && y<decalageY+(j+1)*cote_) y_case_=j;
				}
			
			if(x_case_<2 ) {
				plein_=false;demi_droite_=true;demi_gauche_=false;demi_bas_=false;demi_haut_=false;
			}
			else if(x_case_>6 ) {
				plein_=false;demi_droite_=false;demi_gauche_=true;demi_bas_=false;demi_haut_=false;
			}
			else {
				plein_=true;demi_droite_=false;demi_gauche_=false;demi_bas_=false;demi_haut_=false;
			}
 	   	}
 	   	else { // Landscape
 		   int decalageX = (MainView.largeurPixel-MainView.hauteurPixel)/2;
 		   for(int i=0;i<10;i++)
				for(int j=0;j<10;j++) {
					if(decalageX+i*cote_<x && x<decalageX+(i+1)*cote_) x_case_=i;
					if(j*cote_<y && y<(j+1)*cote_) y_case_=j;
				}
 		   
 		   	if(y_case_<2 ) {
				plein_=false;demi_droite_=false;demi_gauche_=false;demi_bas_=true;demi_haut_=false;
			}
			else if(y_case_>6 ) {
				plein_=false;demi_droite_=false;demi_gauche_=false;demi_bas_=false;demi_haut_=true;
			}
			else {
				plein_=true;demi_droite_=false;demi_gauche_=false;demi_bas_=false;demi_haut_=false;
			}
 	   	}
	}
	
	public void dessineRond(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(couleur_);
		canvas.drawCircle(x_,y_, r_, paint);
		
		paint.setColor(Color.WHITE);
		canvas.drawCircle(x_,y_, r_int_, paint);
		
		paint.setStrokeWidth(4);
		
		if(plein_)
			for(int i=0;i<10;i++) {
				float cosX = (float)cos(0.628318*i);
				float sinY = (float)sin(0.628318*i);
				canvas.drawLine(x_+r_*cosX, y_+r_*sinY, x_,y_, paint);
			}
		
		if(demi_droite_)
			for(int i=0;i<11;i++) {
				float cosX = (float)cos(0.7*(i-5)/2);
				float sinY = (float)sin(0.7*(i-5)/2);
				canvas.drawLine(x_+r_*cosX, y_+r_*sinY, x_,y_, paint);
			}
		if(demi_gauche_)
			for(int i=0;i<11;i++) {
				float cosX = (float)cos(0.7*(i+4)/2);
				float sinY = (float)sin(0.7*(i+4)/2);
				canvas.drawLine(x_+r_*cosX, y_+r_*sinY, x_,y_, paint);
			}
		
		if(demi_haut_)
			for(int i=0;i<11;i++) {
				float cosX = (float)cos(0.7*(i+9)/2-0.187);
				float sinY = (float)sin(0.7*(i+9)/2-0.187);
				canvas.drawLine(x_+r_*cosX, y_+r_*sinY, x_,y_, paint);
			}
		if(demi_bas_)
			for(int i=0;i<11;i++) {
				float cosX = (float)cos(0.7*(i)/2-0.185);
				float sinY = (float)sin(0.7*(i)/2-0.185);
				canvas.drawLine(x_+r_*cosX, y_+r_*sinY, x_,y_, paint);
			}
	}
	
	public void dessineNum(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setFakeBoldText(true);
		if(plein_)
			for(int i=0;i<10;i++) {
				
				float cos = (float)cos(0.628318*(i-3)+0.628318/2);
				float sin = (float)sin(0.628318*(i-3)+0.628318/2);
				
				paint.setTextSize(lib.min(MainView.largeurPixel,MainView.hauteurPixel)/20);
		    	
		    	if(i==0) {
		    		paint.setColor(Color.RED);
		    		canvas.drawText("X",x_+(3*r_/4)*cos,y_+(3*r_/4)*sin,paint);
		    		paint.setColor(Color.WHITE);
		    	}
		    	else canvas.drawText(""+i,x_+(3*r_/4)*cos,y_+(3*r_/4)*sin,paint);
			}
		else if(demi_droite_) {
			for(int i=0;i<10;i++) {
				
				float cos = (float)cos(0.7*(i-5)/2+0.628318/4);
				float sin = (float)sin(0.7*(i-5)/2+0.628318/4);
				
				paint.setTextSize(lib.min(MainView.largeurPixel,MainView.hauteurPixel)/20);
		    	if(i==0) {
		    		paint.setColor(Color.RED);
		    		canvas.drawText("X",x_+(3*r_/4)*cos,y_+(3*r_/4)*sin,paint);
		    		paint.setColor(Color.WHITE);
		    	}
		    	else canvas.drawText(""+i,x_+(3*r_/4)*cos,y_+(3*r_/4)*sin,paint);
			}
		}
		else if(demi_gauche_) {
			for(int i=0;i<10;i++) {
				
				float cos = (float)cos(0.7*(i+9)/2+0.02);
				float sin = (float)sin(0.7*(i+9)/2+0.02);
				
				paint.setTextSize(lib.min(MainView.largeurPixel,MainView.hauteurPixel)/20);
		    	if(i==0) {
		    		paint.setColor(Color.RED);
		    		canvas.drawText("X",x_+(3*r_/4)*sin,y_+(3*r_/4)*cos,paint);
		    		paint.setColor(Color.WHITE);
		    	}
		    	else canvas.drawText(""+i,x_+(3*r_/4)*sin,y_+(3*r_/4)*cos,paint);
			}
		}
		
		else if(demi_haut_) {
			for(int i=0;i<10;i++) {
				
				float cos = (float)cos(0.7*(i+5)/2-0.19);
				float sin = (float)sin(0.7*(i+5)/2-0.19);
				
				paint.setTextSize(lib.min(MainView.largeurPixel,MainView.hauteurPixel)/20);
		    	if(i==9) {
		    		paint.setColor(Color.RED);
		    		canvas.drawText("X",x_+(3*r_/4)*sin,y_+(3*r_/4)*cos,paint);
		    		paint.setColor(Color.WHITE);
		    	}
		    	else canvas.drawText(""+(9-i),x_+(3*r_/4)*sin,y_+(3*r_/4)*cos,paint);
			}
		}
		
		else if(demi_bas_) {
			for(int i=0;i<10;i++) {
				
				float cos = (float)cos(0.7*(i+14)/2-0.19);
				float sin = (float)sin(0.7*(i+14)/2-0.19);
				
				paint.setTextSize(lib.min(MainView.largeurPixel,MainView.hauteurPixel)/20);
		    	if(i==0) {
		    		paint.setColor(Color.RED);
		    		canvas.drawText("X",x_+(3*r_/4)*sin,y_+(3*r_/4)*cos,paint);
		    		paint.setColor(Color.WHITE);
		    	}
		    	else canvas.drawText(""+(i),x_+(3*r_/4)*sin,y_+(3*r_/4)*cos,paint);
			}
		}
	}
	
	public void selection(Grille p, int x_up, int y_up, boolean brouillon) {
		
		if(!brouillon) {
			/* Aucune case n'est selectionnee */
			if(x_case_==-1 || y_case_==-1) return;
			/* Est dans le cercle interieur */
			if(x_-r_int_/2<x_up && x_up<x_+r_int_/2 && y_-r_int_/2<y_up && y_up<y_+r_int_/2) return;
			/* Est à l'exterieur */
			if( x_up<x_-r_ || x_+r_<x_up || y_up<y_-r_ || y_+r_<y_up) return;
			
			/* Erreur */
			if(!(-1<x_case_ && x_case_<9 && -1<y_case_ && y_case_<9)) return;
			
			if(plein_) {
				if(x_-r_int_/2<x_up && x_up<x_+r_int_/2) {
					if(y_up<y_) 	p.setValeurCase(x_case_,y_case_,0);
					else 			p.setValeurCase(x_case_,y_case_,5);
				}
				else if(y_-cote_< y_up && y_up<y_) {
					if(x_up<x_) 	p.setValeurCase(x_case_,y_case_,8);
					else 			p.setValeurCase(x_case_,y_case_,2);
				} 
				else if(y_< y_up && y_up<y_+cote_) {
					if(x_up<x_) 	p.setValeurCase(x_case_,y_case_,7);
					else 			p.setValeurCase(x_case_,y_case_,3);
				} 
				else if(y_up< y_ && x_up<x_)
					p.setValeurCase(x_case_,y_case_,9);
				else if(y_up< y_ && x_up>x_)
					p.setValeurCase(x_case_,y_case_,1);
				else if(y_up> y_ && x_up<x_)
					p.setValeurCase(x_case_,y_case_,6);
				else if(y_up> y_ && x_up>x_)
					p.setValeurCase(x_case_,y_case_,4);
				else // erreur
					p.setValeurCase(x_case_,y_case_,0);
			}
			else if(demi_droite_) {
				if(x_-r_int_/2<x_up && x_up<x_+r_int_/2) {
					if(y_up<y_) 	p.setValeurCase(x_case_,y_case_,0);
					else 			p.setValeurCase(x_case_,y_case_,9);
				}
				else if( y_up< y_ && y_up> y_-cote_/2 )
					p.setValeurCase(x_case_,y_case_,4);
				else if( y_up> y_ && y_up< y_+cote_/2   )
					p.setValeurCase(x_case_,y_case_,5);
				else if(x_+r_int_/2<=x_up && x_up<x_+5*r_int_/6) {
					if(y_up<y_) 	p.setValeurCase(x_case_,y_case_,1);
					else 			p.setValeurCase(x_case_,y_case_,8);
				}
				else if(y_up<y_) {
					int abs = x_up-x_; int ord = y_-y_up;
					if(ord>abs) 	p.setValeurCase(x_case_,y_case_,2);
					else 			p.setValeurCase(x_case_,y_case_,3);
				}
				else {
					int abs = x_up-x_; int ord = y_up-y_;
					if(ord>abs) 	p.setValeurCase(x_case_,y_case_,7);
					else 			p.setValeurCase(x_case_,y_case_,6);
				}
			}
			else if(demi_gauche_) {
				if(x_-r_int_/2<x_up && x_up<x_+r_int_/2) {
					if(y_up<y_) 	p.setValeurCase(x_case_,y_case_,0);
					else 			p.setValeurCase(x_case_,y_case_,9);
				}
				else if( y_up< y_ && y_up> y_-cote_/2 )
					p.setValeurCase(x_case_,y_case_,4);
				else if( y_up> y_ && y_up< y_+cote_/2   )
					p.setValeurCase(x_case_,y_case_,5);
				else if(x_-5*r_int_/6<=x_up && x_up<x_-r_int_/2) {
					if(y_up<y_) 	p.setValeurCase(x_case_,y_case_,1);
					else 			p.setValeurCase(x_case_,y_case_,8);
				}
				else if(y_up<y_) {
					int abs = x_-x_up; int ord = y_-y_up;
					if(ord>abs) 	p.setValeurCase(x_case_,y_case_,2);
					else 			p.setValeurCase(x_case_,y_case_,3);
				}
				else {
					int abs = x_-x_up; int ord = y_up-y_;
					if(ord>abs) 	p.setValeurCase(x_case_,y_case_,7);
					else 			p.setValeurCase(x_case_,y_case_,6);
				}
			}
			else if(demi_haut_) {
				if(y_-r_int_/2<y_up && y_up<y_+r_int_/2) {
					if(x_up<x_) 	p.setValeurCase(x_case_,y_case_,0);
					else 			p.setValeurCase(x_case_,y_case_,9);
				}
				else if( x_up< x_ && x_up> x_-cote_/2 )
					p.setValeurCase(x_case_,y_case_,4);
				else if( x_up> x_ && x_up< x_+cote_/2   )
					p.setValeurCase(x_case_,y_case_,5);
				else if(y_-5*r_int_/6<=y_up && y_up<y_-r_int_/2) {
					if(x_up<x_) 	p.setValeurCase(x_case_,y_case_,1);
					else 			p.setValeurCase(x_case_,y_case_,8);
				}
				else if(x_up<x_) {
					int abs = x_-x_up; int ord = y_-y_up;
					if(ord<abs) 	p.setValeurCase(x_case_,y_case_,2);
					else 			p.setValeurCase(x_case_,y_case_,3);
				}
				else {
					int abs = x_-x_up; int ord = y_up-y_;
					if(ord<abs) 	p.setValeurCase(x_case_,y_case_,6);
					else 			p.setValeurCase(x_case_,y_case_,7);
				}
			}
			
			else if(demi_bas_) {
				if(y_-r_int_/2<y_up && y_up<y_+r_int_/2) {
					if(x_up<x_) 	p.setValeurCase(x_case_,y_case_,0);
					else 			p.setValeurCase(x_case_,y_case_,9);
				}
				else if( x_up< x_ && x_up> x_-cote_/2 )
					p.setValeurCase(x_case_,y_case_,4);
				else if( x_up> x_ && x_up< x_+cote_/2   )
					p.setValeurCase(x_case_,y_case_,5);
				else if(y_+5*r_int_/6>=y_up && y_up>y_+r_int_/2) {
					if(x_up<x_) 	p.setValeurCase(x_case_,y_case_,1);
					else 			p.setValeurCase(x_case_,y_case_,8);
				}
				else if(x_up<x_) {
					int abs = x_-x_up; int ord = y_-y_up;
					if(-ord<abs) 	p.setValeurCase(x_case_,y_case_,2);
					else 			p.setValeurCase(x_case_,y_case_,3);
				}
				else {
					int abs = x_-x_up; int ord = y_up-y_;
					if(-ord<abs) 	p.setValeurCase(x_case_,y_case_,6);
					else 			p.setValeurCase(x_case_,y_case_,7);
				}
			}		
		}
		else {
			
			/* Aucune case n'est selectionnee */
			if(x_case_==-1 || y_case_==-1) return;
			/* Est dans le cercle interieur */
			if(x_-r_int_/2<x_up && x_up<x_+r_int_/2 && y_-r_int_/2<y_up && y_up<y_+r_int_/2) return;
			/* Est à l'exterieur */
			if( x_up<x_-r_ || x_+r_<x_up || y_up<y_-r_ || y_+r_<y_up) return;
			
			/* Erreur */
			if(!(-1<x_case_ && x_case_<9 && -1<y_case_ && y_case_<9)) return;
			
			if(plein_) {
				if(x_-r_int_/2<x_up && x_up<x_+r_int_/2) {
					if(y_up<y_) 	p.zeroBrouillon(x_case_,y_case_);
					else 			p.setValeurBrouillon(x_case_,y_case_,5);
				}
				else if(y_-cote_< y_up && y_up<y_) {
					if(x_up<x_) 	p.setValeurBrouillon(x_case_,y_case_,8);
					else 			p.setValeurBrouillon(x_case_,y_case_,2);
				} 
				else if(y_< y_up && y_up<y_+cote_) {
					if(x_up<x_) 	p.setValeurBrouillon(x_case_,y_case_,7);
					else 			p.setValeurBrouillon(x_case_,y_case_,3);
				} 
				else if(y_up< y_ && x_up<x_)
					p.setValeurBrouillon(x_case_,y_case_,9);
				else if(y_up< y_ && x_up>x_)
					p.setValeurBrouillon(x_case_,y_case_,1);
				else if(y_up> y_ && x_up<x_)
					p.setValeurBrouillon(x_case_,y_case_,6);
				else if(y_up> y_ && x_up>x_)
					p.setValeurBrouillon(x_case_,y_case_,4);
				else // erreur
					p.setValeurBrouillon(x_case_,y_case_,0);
			}
			else if(demi_droite_) {
				if(x_-r_int_/2<x_up && x_up<x_+r_int_/2) {
					if(y_up<y_) 	p.zeroBrouillon(x_case_,y_case_);
					else 			p.setValeurBrouillon(x_case_,y_case_,9);
				}
				else if( y_up< y_ && y_up> y_-cote_/2 )
					p.setValeurBrouillon(x_case_,y_case_,4);
				else if( y_up> y_ && y_up< y_+cote_/2   )
					p.setValeurBrouillon(x_case_,y_case_,5);
				else if(x_+r_int_/2<=x_up && x_up<x_+5*r_int_/6) {
					if(y_up<y_) 	p.setValeurBrouillon(x_case_,y_case_,1);
					else 			p.setValeurBrouillon(x_case_,y_case_,8);
				}
				else if(y_up<y_) {
					int abs = x_up-x_; int ord = y_-y_up;
					if(ord>abs) 	p.setValeurBrouillon(x_case_,y_case_,2);
					else 			p.setValeurBrouillon(x_case_,y_case_,3);
				}
				else {
					int abs = x_up-x_; int ord = y_up-y_;
					if(ord>abs) 	p.setValeurBrouillon(x_case_,y_case_,7);
					else 			p.setValeurBrouillon(x_case_,y_case_,6);
				}
			}
			else if(demi_gauche_) {
				if(x_-r_int_/2<x_up && x_up<x_+r_int_/2) {
					if(y_up<y_) 	p.zeroBrouillon(x_case_,y_case_);
					else 			p.setValeurBrouillon(x_case_,y_case_,9);
				}
				else if( y_up< y_ && y_up> y_-cote_/2 )
					p.setValeurBrouillon(x_case_,y_case_,4);
				else if( y_up> y_ && y_up< y_+cote_/2   )
					p.setValeurBrouillon(x_case_,y_case_,5);
				else if(x_-5*r_int_/6<=x_up && x_up<x_-r_int_/2) {
					if(y_up<y_) 	p.setValeurBrouillon(x_case_,y_case_,1);
					else 			p.setValeurBrouillon(x_case_,y_case_,8);
				}
				else if(y_up<y_) {
					int abs = x_-x_up; int ord = y_-y_up;
					if(ord>abs) 	p.setValeurBrouillon(x_case_,y_case_,2);
					else 			p.setValeurBrouillon(x_case_,y_case_,3);
				}
				else {
					int abs = x_-x_up; int ord = y_up-y_;
					if(ord>abs) 	p.setValeurBrouillon(x_case_,y_case_,7);
					else 			p.setValeurBrouillon(x_case_,y_case_,6);
				}
			}
		}
		
		x_case_=-1; y_case_=-1;
	}
	
	public double sin(double deg) {return Math.sin(deg);}
	public double cos(double deg) {return Math.cos(deg);}
}
