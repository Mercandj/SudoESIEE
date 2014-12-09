package com.esiee.sudoku;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 
 * Classe bouton qui creer des bontons en style "flat".
 * Ses bontons ont 2 couleurs afin de changer de couleur lors du clique.
 * 
 * @author JM
 *
 */
public class Bouton {
	
	/* Valeur = un etat ou l'autre */
	private boolean valeur_;
		public boolean getValeur() {return valeur_;}
		public void setValeur(Boolean p) {valeur_=p;}
	
	/* Pre click = appui DOWN sur le bouton mais pas UP */
	private boolean pre_click = false;
		public void preClick() {pre_click=true;}
		public boolean getPreClick() {return pre_click;}
	
	private int x1_portrait_,y1_portrait_,x2_portrait_,y2_portrait_;
	private int couleur_1_;
	private int couleur_2_;
	private int couleur_texte_;
	private String texte_1_;
	private String texte_2_;
		
	public Bouton (boolean valeur, int x1_portrait, int y1_portrait,int x2_portrait, int y2_portrait, 
			String texte_1, String texte_2, int couleur_1, int couleur_2, int couleur_texte) {
		this.valeur_=valeur;
		this.x1_portrait_=x1_portrait;
		this.y1_portrait_=y1_portrait;
		this.x2_portrait_=x2_portrait;
		this.y2_portrait_=y2_portrait;
		this.texte_1_=texte_1;
		this.texte_2_=texte_2;
		this.couleur_1_=couleur_1;
		this.couleur_2_=couleur_2;
		this.couleur_texte_=couleur_texte;
	}
	
	public void dessine_portrait(Canvas canvas) {
		int hauteur_texte = 5*(y2_portrait_-y1_portrait_)/12;
		Paint paint = new Paint();
		if(!valeur_) paint.setColor(couleur_1_);
		else paint.setColor(couleur_2_);
 		canvas.drawRect(x1_portrait_,y1_portrait_,x2_portrait_,y2_portrait_, paint);
 		paint.setColor(couleur_texte_);
 		paint.setTextSize(hauteur_texte);
 	    paint.setFakeBoldText(true);
 	   if(!valeur_) canvas.drawText(texte_1_,(x2_portrait_+x1_portrait_)/2-(int)paint.measureText(texte_1_)/2,(y2_portrait_+y1_portrait_)/2+hauteur_texte/2,paint);
 	   else canvas.drawText(texte_2_,(x2_portrait_+x1_portrait_)/2-(int)paint.measureText(texte_2_)/2,(y2_portrait_+y1_portrait_)/2+hauteur_texte/2,paint);
	}
	
	public boolean estDansBouton_portrait(int x, int y) {
		if(x1_portrait_<x && x<x2_portrait_ && y1_portrait_<y && y<y2_portrait_) return true;
		return false;
	}
	
	/**
	 * Changement des attributs lors d'un clic
	 */
	public void clic() {
		valeur_= !valeur_;
		pre_click=false;
	}
	
	public void deblock() {
		valeur_= false;
		pre_click=false;
	}
}
