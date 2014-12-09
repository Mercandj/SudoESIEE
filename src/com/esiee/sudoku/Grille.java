package com.esiee.sudoku;

public class Grille {
	
	private boolean gain = false;
		public boolean gain() {return gain;}
		public void setGain(boolean p) {gain=p;}
	
	private Case[][] matrice_;
		public Case[][] getMatrice() {return matrice_;}
		public Case getCase(int i, int j) {return matrice_[i][j];}
		public int getValeurCase(int i, int j) {return matrice_[i][j].getValeur();}
		public void setValeurCase(int i, int j, int val) {matrice_[i][j].setValeur(val);}
		public void setValeurBrouillon(int i, int j, int val) {matrice_[i][j].setBrouillon(val);}
		public void zeroBrouillon(int i, int j) {matrice_[i][j].zeroBrouillon();}
		public void brouillonsTrue() {
			for(int i=0;i<9;i++)
				for(int j=0;j<9;j++)
					if(getValeurCase(i,j)==0)	matrice_[i][j].brouillonsTrue();
		}
		public void brouillonsFalse() {
			for(int i=0;i<9;i++)
				for(int j=0;j<9;j++)
					if(getValeurCase(i,j)==0)	matrice_[i][j].brouillonsFalse();
		}
		public int getValeurBrouillon(int i, int j, int num) {
			int val=0;
			for(int v=1;v<10;v++) {
				if(matrice_[i][j].getBrouillon(v)) {
					if(val==num) return v;
					val++;
				}
			}
			return 0;
		}
		public int getNombreBrouillons(int i, int j) {
			int val=0;
			for(int v=1;v<10;v++)
				if(matrice_[i][j].getBrouillon(v)) val++;
			return val;
		}
		public int getPremierBrouillon(int i, int j) {
			for(int v=1;v<10;v++)
				if(matrice_[i][j].getBrouillon(v)) return v;
			return 0;
		}
	
	public Grille() {
		this.matrice_ = new Case[9][9];
		for(int i=0; i<9; i++) 
			for(int j=0; j<9; j++) 
				matrice_[i][j] = new Case(0);
	}
}
