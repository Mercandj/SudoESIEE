package com.esiee.sudoku;

public class Case {
	
	private boolean protected_=false;
		public boolean isProtected() {return protected_;}
		public void setProtected(final boolean p) {protected_=p;}
	
	private int valeur_;
		public int getValeur() {return valeur_;}
		public void setValeur(int val) {if(!isProtected()) valeur_=val;}
		
	private boolean[] brouillon_;
		public boolean[] getBrouillon() {return brouillon_;}
		public boolean getBrouillon(int index) {return brouillon_[index-1];}
		public void setBrouillon(int index, boolean valeur) {brouillon_[index-1]=valeur;}
		public void setBrouillon(boolean[] tab) {
			if(tab.length!=9) return; for(int i=0;i<9;i++) brouillon_[i]=tab[i];
		}
		public void setBrouillon(int val) {brouillon_[val-1]=!brouillon_[val-1];}
		public void zeroBrouillon() {brouillon_=new boolean[9];}
		public void brouillonsTrue() {brouillon_=new boolean[9]; for(int i=0;i<9;i++)brouillon_[i]=true;}
		public void brouillonsFalse() {brouillon_=new boolean[9]; for(int i=0;i<9;i++)brouillon_[i]=false;}
	
	public Case(int valeur) {
		this.valeur_=valeur;
		this.brouillon_= new boolean[9];
	}

	
}
