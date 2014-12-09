package com.esiee.sudoku;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class MainView extends SurfaceView implements SurfaceHolder.Callback {
	
	private Grille grille_ = new Grille();
		public int getValeurCase(int i, int j) {return this.grille_.getValeurCase(i,j);}
		public Grille getGrille() {return this.grille_;}
		public int getValeurBrouillon(int i, int j, int num) {return this.grille_.getValeurBrouillon(i,j,num);}
		public void setGrille(Grille g) {this.grille_ = g;}
		
	private int[][] matrice_brouillon_ = new int[9][9];
		public int[][] getMatriceBrouillon() {return matrice_brouillon_;}
		
	private Cercle_Saisie cercle_;
		public Cercle_Saisie getCercle() {return cercle_;}
		
	private Bouton bouton_brouillon_ = null;
	private BoutonPoussoir bouton_resoudre_ = null;
	private BoutonPoussoir bouton_effacer_ = null;
	private BoutonPoussoir bouton_new_ = null;
		public BoutonPoussoir get_bouton_new_() {return bouton_new_;}
	
	private int couleur_non_brouillon_ = Color.rgb(10, 83, 180);
	private int couleur_brouillon_ = Color.rgb(10, 180, 70);
	
	private int x_down, y_down, x_move, y_move, x_up, y_up;
	public static int hauteurPixel, largeurPixel;

	private Paint paint = new Paint();
	private MainViewThread thread; 
	
	public MainViewThread gethread() {
		return thread; 
	}
	
	/***************************************************/
	/****                 THREAD                    ****/
	/***************************************************/
	class MainViewThread extends Thread { 
       
       private int mCanvasHeight = 1; 
       private int mCanvasWidth = 1; 
       private boolean mRun = false; 
       private SurfaceHolder mSurfaceHolder; 
       private int minutes;
       private int secondes;

       public MainViewThread(SurfaceHolder surfaceHolder, Context context, Handler handler) {  
           mSurfaceHolder = surfaceHolder; 
           
			Timer t = new Timer();
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					secondes++;
					if (secondes == 60) {
						minutes++;
						secondes = 0;
					}
					thread.repaint();
				}
			};
			t.scheduleAtFixedRate(task, 0, 1000);
       } 

       public void doStart() { 
           synchronized (mSurfaceHolder) { 
           } 
       } 

       public void pause() { 
           synchronized (mSurfaceHolder) { 
           } 
       } 

       public synchronized void restoreState(Bundle savedState) { 
           synchronized (mSurfaceHolder) { 
           } 
       } 

       public void repaint() {
    	   Canvas c = null;
    	   try {
    		   c = mSurfaceHolder.lockCanvas();
    		   if (c!=null){
    			   synchronized (mSurfaceHolder) {
    				   doDraw(c);				// dessine le canvas
    			   }
    		   }
    	   } finally {
    		   if (c != null) {
    			   mSurfaceHolder.unlockCanvasAndPost(c);
    		   }
    	   }
       }
       
       @Override 
       public void run() { 
    	   repaint();
       } 

       public void setRunning(boolean b) { 
           mRun = b; 
       } 
       
       public void setSurfaceSize(int width, int height) { 
           synchronized (mSurfaceHolder) { 
               mCanvasWidth = width; 
               mCanvasHeight = height; 
           } 
       } 

       public void unpause() { 
           synchronized (mSurfaceHolder) { 
           } 
       } 

       boolean doKeyDown(int keyCode, KeyEvent msg) { 
           synchronized (mSurfaceHolder) { 
               return false; 
           } 
       } 

       boolean doKeyUp(int keyCode, KeyEvent msg) { 
           boolean handled = false; 
           synchronized (mSurfaceHolder) { 
           } 
           return handled; 
       }

       private void doDraw(Canvas canvas) { 
    	   	hauteurPixel = mCanvasHeight;
    	   	largeurPixel = mCanvasWidth;
   			
    	   	dessineBackground(canvas);
    	   	if (largeurPixel < hauteurPixel) if(bouton_brouillon_!=null) bouton_brouillon_.dessine_portrait(canvas); //dessineBoutons(canvas);
    	   	if (largeurPixel < hauteurPixel) if(bouton_resoudre_!=null) bouton_resoudre_.dessine_portrait(canvas);
    	   	if (largeurPixel < hauteurPixel) if(bouton_effacer_!=null) bouton_effacer_.dessine_portrait(canvas);
    	   	if (largeurPixel < hauteurPixel) if(bouton_new_!=null) bouton_new_.dessine_portrait(canvas);
    	   	dessineMatrices(canvas);
    	   	if(cercle_!=null) cercle_.dessineRond(canvas);
    	   	dessineTableau(canvas);
    	   	if(cercle_!=null) cercle_.dessineNum(canvas);
    	   	dessineTimer(canvas);
    	   	if(grille_.gain()) dessineGain(canvas);
   			
       } // Fin du doDraw
       
       
       private void dessineGain(Canvas canvas) {
    	   paint.setColor(couleur_brouillon_);
    	   int hauteur_txt = lib.min(largeurPixel,hauteurPixel)/14;
    	   paint.setTextSize(hauteur_txt);
    	   paint.setFakeBoldText(true);
    	   canvas.drawText("BRAVO",largeurPixel/2,3*hauteur_txt/2,paint);
       }
       
       private void dessineTimer(Canvas canvas) {
    	   paint.setColor(Color.BLACK);
    	   int hauteur_txt = lib.min(largeurPixel,hauteurPixel)/14;
    	   paint.setTextSize(hauteur_txt);
    	   paint.setFakeBoldText(true);
    	   canvas.drawText(minutes+" : "+secondes,10,3*hauteur_txt/2,paint);
       }
       
       private void dessineBackground(Canvas canvas) {
    	   paint.setColor(Color.WHITE);
  		   canvas.drawRect(0, 0, largeurPixel, hauteurPixel, paint);
       }
       
       private void dessineMatrices(Canvas canvas) {
    	   if (largeurPixel < hauteurPixel) { // Portrait
    		   int decalageY = (hauteurPixel-largeurPixel)/2;
    		   dessineMatrice(canvas, largeurPixel/9, 0, decalageY);
    		   dessineMatriceBrouillon(canvas, largeurPixel/9, 0, decalageY);
    	   }
    	   else { // Landscape
    		   int decalageX = (largeurPixel-hauteurPixel)/2;
    		   dessineMatrice(canvas, hauteurPixel/9, decalageX, 0);
    		   dessineMatriceBrouillon(canvas, hauteurPixel/9, decalageX, 0);
    	   }  
       }
       
       private void dessineMatrice(Canvas canvas, int coteCarreau, int decalageX, int decalageY) {
    	   int hauteur_txt = lib.min(largeurPixel,hauteurPixel)/14;
    	   paint.setColor(couleur_non_brouillon_);
    	   paint.setTextSize(hauteur_txt);
    	   paint.setFakeBoldText(true);
    	   for(int i=0;i<9;i++)
    		   for(int j=0;j<9;j++)
    			   if(0<getValeurCase(i,j) && getValeurCase(i,j)<10) {
    				   if(grille_.getCase(i,j).isProtected()) paint.setColor(Color.BLACK);
    				   else paint.setColor(couleur_non_brouillon_);
    				   canvas.drawText(""+getValeurCase(i,j),i*coteCarreau+coteCarreau/2-(int)paint.measureText(""+getValeurCase(i,j))/2+decalageX,j*coteCarreau+coteCarreau/2+hauteur_txt/2+decalageY,paint);
    			   }
    	   }
       
       private void dessineMatriceBrouillon(Canvas canvas, int coteCarreau, int decalageX, int decalageY) {
    	   int hauteur_txt = lib.min(largeurPixel,hauteurPixel)/26;
    	   paint.setColor(couleur_brouillon_);
    	   paint.setTextSize(hauteur_txt);
    	   paint.setFakeBoldText(true);
    	   int nb_brouillon=0;
    	   for(int i=0;i<9;i++)
    		   for(int j=0;j<9;j++) {
    			   for(int v=0;v<9;v++) {
	    			   if(0<getValeurBrouillon(i,j,nb_brouillon) && getValeurBrouillon(i,j,nb_brouillon)<10) {
	    				   if(nb_brouillon<3) canvas.drawText(""+getValeurBrouillon(i,j,nb_brouillon),-7*v*coteCarreau/24+i*coteCarreau+4*coteCarreau/5-(int)paint.measureText(""+getValeurCase(i,j))/2+decalageX,j*coteCarreau+coteCarreau/6+hauteur_txt/2+decalageY,paint);
	    				   else if(nb_brouillon<5) canvas.drawText(""+getValeurBrouillon(i,j,nb_brouillon),-7*coteCarreau/12+i*coteCarreau+4*coteCarreau/5-(int)paint.measureText(""+getValeurCase(i,j))/2+decalageX,7*(v-2)*coteCarreau/24+j*coteCarreau+coteCarreau/6+hauteur_txt/2+decalageY,paint);
	    				   else if(nb_brouillon<7) canvas.drawText(""+getValeurBrouillon(i,j,nb_brouillon),-7*(-v+6)*coteCarreau/24+i*coteCarreau+4*coteCarreau/5-(int)paint.measureText(""+getValeurCase(i,j))/2+decalageX,7*coteCarreau/12+j*coteCarreau+coteCarreau/6+hauteur_txt/2+decalageY,paint);
	    				   nb_brouillon++;
	    			   }
    			   }
    			   nb_brouillon=0;
    		   }
       }
       
       private void dessineTableau(Canvas canvas) {
    	   if (largeurPixel < hauteurPixel) { // Portrait
    		   int decalageY = (hauteurPixel-largeurPixel)/2;
    		   dessineTableau(canvas, largeurPixel/9, 0, decalageY);
    	   }
    	   else { // Landscape
    		   int decalageX = (largeurPixel-hauteurPixel)/2;
    		   dessineTableau(canvas, hauteurPixel/9, decalageX, 0);
    	   }  
       }
       
       private void dessineTableau(Canvas canvas, int coteCarreau, int decalageX, int decalageY) {
    	   	paint.setColor(Color.BLACK);
			for (int i = 0; i < 10; i++) {
				if ((i) % 3 == 0) paint.setStrokeWidth(6 );
				else paint.setStrokeWidth(3 );
				canvas.drawLine(decalageX, i*coteCarreau+decalageY, largeurPixel-decalageX, i*coteCarreau+decalageY, paint);
				canvas.drawLine(i*coteCarreau+decalageX, decalageY, i*coteCarreau+decalageX, hauteurPixel-decalageY, paint);
			}
       }
       
       
	}
	/***************************************************/
	/****                 FIN THREAD                ****/
	/***************************************************/ 
	
	public MainView(Context context) {
		super(context);
		
		Display display = MainActivity.activity.getWindowManager().getDefaultDisplay(); 
		int largeurPixel = display.getWidth();
		int hauteurPixel = display.getHeight();
		
		if(largeurPixel > hauteurPixel) {
			int tmp;
			tmp = largeurPixel;
			largeurPixel = hauteurPixel;
			hauteurPixel = tmp;
		}
		
		int coteCarre = lib.min(largeurPixel/9,hauteurPixel/9);
		int decalageY = (hauteurPixel-largeurPixel)/2+coteCarre/2;
		
		
		/* Creation des boutons */
		
		bouton_brouillon_ = new Bouton(false , -2*coteCarre+largeurPixel/3, hauteurPixel-decalageY+coteCarre+2*coteCarre/3, -2*coteCarre+2*largeurPixel/3, hauteurPixel-decalageY+2*coteCarre+2*coteCarre/3,
				MainActivity.activity.getString(R.string.bouton_brouillon_OFF_),MainActivity.activity.getString(R.string.bouton_brouillon_ON_),couleur_non_brouillon_,couleur_brouillon_,Color.WHITE);
		
		bouton_new_ = new BoutonPoussoir(false , 2*coteCarre+largeurPixel/3, hauteurPixel-decalageY+coteCarre+2*coteCarre/3, 2*coteCarre+2*largeurPixel/3, hauteurPixel-decalageY+2*coteCarre+2*coteCarre/3,
				MainActivity.activity.getString(R.string.bouton_new_), MainActivity.activity.getString(R.string.bouton_new_),couleur_non_brouillon_,couleur_brouillon_,Color.WHITE);
		
		bouton_resoudre_ = new BoutonPoussoir(false , -2*coteCarre+largeurPixel/3, decalageY/10 +lib.min(largeurPixel/11,hauteurPixel/11)+decalageY/10, -2*coteCarre+2*largeurPixel/3, decalageY/10 +lib.min(largeurPixel/11,hauteurPixel/11)+decalageY/10+coteCarre,
				MainActivity.activity.getString(R.string.bouton_resoudre_),MainActivity.activity.getString(R.string.bouton_resoudre_),couleur_non_brouillon_,couleur_brouillon_,Color.WHITE);
		
		bouton_effacer_ = new BoutonPoussoir(false , 2*coteCarre+largeurPixel/3, decalageY/10 +lib.min(largeurPixel/11,hauteurPixel/11)+decalageY/10, 2*coteCarre+2*largeurPixel/3, decalageY/10 +lib.min(largeurPixel/11,hauteurPixel/11)+decalageY/10+coteCarre,
				MainActivity.activity.getString(R.string.bouton_effacer_),MainActivity.activity.getString(R.string.bouton_effacer_),couleur_non_brouillon_,couleur_brouillon_,Color.WHITE);
		

		// ecoute les changement de surface 
		SurfaceHolder holder = getHolder(); 
		holder.addCallback(this); 

		// creer le thread : lance surfaceCreated() 
		thread = new MainViewThread(holder, context, new Handler() { 
			@Override 
			public void handleMessage(Message m) { 
			} 
		}); 
		
		setKeepScreenOn(true);
		setFocusable(true); // être sur d'avoir les events
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		x_move = (int)event.getX(0);
		y_move = (int)event.getY(0);
		
		switch(event.getAction()) {
	    	case MotionEvent.ACTION_DOWN:	
	    		x_down= (int)event.getX(0);	y_down= (int)event.getY(0);
	    		if(bouton_brouillon_.estDansBouton_portrait(x_down,y_down)) 
	    			bouton_brouillon_.preClick();
	    		else if(bouton_resoudre_.estDansBouton_portrait(x_down,y_down)) 
	    			bouton_resoudre_.preClick();
	    		else if(bouton_effacer_.estDansBouton_portrait(x_down,y_down)) 
	    			bouton_effacer_.preClick();
	    		else if(bouton_new_.estDansBouton_portrait(x_down,y_down)) 
	    			bouton_new_.preClick();
	    		else
		    		if(cercle_==null && estDansMatrice(x_down,y_down)) {
		    			if(!bouton_brouillon_.getValeur())
		    				cercle_ = new Cercle_Saisie(x_down,y_down,7*lib.min(hauteurPixel, largeurPixel)/24,couleur_non_brouillon_);
		    			else
		    				cercle_ = new Cercle_Saisie(x_down,y_down,7*lib.min(hauteurPixel, largeurPixel)/24,couleur_brouillon_);
		    		}
	    		break;
	    	case MotionEvent.ACTION_UP:		
	    		x_up= (int)event.getX(0);	y_up= (int)event.getY(0);
	    		
	    		if(bouton_brouillon_.estDansBouton_portrait(x_up,y_up) && bouton_brouillon_.getPreClick())
	    			bouton_brouillon_.clic();
	    		else if(bouton_resoudre_.estDansBouton_portrait(x_up,y_up) && bouton_resoudre_.getPreClick()) {
	    			Grille tmp = new Grille();
	    			lib.copieGrille(tmp,grille_);
	    			grille_.brouillonsTrue();
	    			grille_=lib.resoudreGrille(grille_);
	    			if(grille_==null) {
	    				Log.d("MainView", "La grille renvoyée est nulle"); //MainActivity.activity.finish();
	    				grille_=new Grille();
	    				lib.copieGrille(grille_,tmp);
	    			}
	    			bouton_resoudre_.clic();
	    		}
	    		else if(bouton_effacer_.estDansBouton_portrait(x_up,y_up) && bouton_effacer_.getPreClick()) {
	    			grille_=lib.effacerGrille(grille_);
	    			bouton_effacer_.clic();
	    		}
	    		else if(bouton_new_.estDansBouton_portrait(x_up,y_up) && bouton_new_.getPreClick()) {
	    			if(!bouton_new_.block)
	    				lib.genereGrilleTask();
	    			bouton_new_.clic();
	    		}
	    		else {
	    			
    				bouton_resoudre_.deblock();
	    			bouton_effacer_.deblock();
	    			bouton_new_.deblock();
	    			
		    		if(cercle_!=null) {
		    			if(!bouton_brouillon_.getValeur())
		    				cercle_.selection(grille_,x_up,y_up,false);
		    			else
		    				cercle_.selection(grille_,x_up,y_up,true);
		    			if(lib.gain(grille_)) {
		    				grille_.setGain(true);
		    				Toast.makeText(MainActivity.activity, "Gain : "+lib.gain(grille_),Toast.LENGTH_SHORT).show();
		    			}
		    			lib.sauvegarde();
		    		}
	    		}
	    		cercle_ = null;
	    		break;
	    	case MotionEvent.ACTION_MOVE: 	
		}
		
		// Remet les variables du tactile à zero
		x_down=0; y_down=0; x_move=0; y_move=0; x_up=0; y_up=0;
		
		// Appel ondraw
		thread.repaint();
		return true;
	} // Fin onTouch
	
	public boolean estDansMatrice(int x, int y) {
		int decalageY = (hauteurPixel-largeurPixel)/2;
		int decalageX = (largeurPixel-hauteurPixel)/2;
		if (largeurPixel < hauteurPixel) { //Portrait
			if(decalageY<y && y<hauteurPixel-decalageY) return true;
			return false;
		} else {
			if(decalageX<x && x<largeurPixel-decalageX) return true;
			return false;
		}
	}
	
	public MainViewThread getThread() { 
		return thread; 
	} 

	@Override 
	public boolean onKeyDown(int keyCode, KeyEvent msg) { 
		return thread.doKeyDown(keyCode, msg); 
	} 

	@Override 
	public boolean onKeyUp(int keyCode, KeyEvent msg) { 
		return thread.doKeyUp(keyCode, msg); 
	} 

	@Override 
	public void onWindowFocusChanged(boolean hasWindowFocus) { 
		if (!hasWindowFocus) thread.pause();
	} 

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) { 
		thread.setSurfaceSize(width, height); 
	} 

	public void surfaceCreated(SurfaceHolder holder) { 
	   	if (thread.getState() == Thread.State.TERMINATED) thread = new MainViewThread(getHolder(), getContext(), getHandler());
	   	thread.setRunning(true);
	   	thread.start();
	} 

	public void surfaceDestroyed(SurfaceHolder holder) { 
		boolean retry = true; 
		thread.setRunning(false); 
		while (retry) { 
			try { 
				thread.join();						// supprime le thread
				retry = false; 
			} catch (InterruptedException e) { 
			} 
		} 
	}
}