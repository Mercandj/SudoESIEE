package com.esiee.sudoku;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;

public class lib_TXT {

	public static void ecrire_droid(String txt) {
		mFile = new File(MainActivity.activity.getFilesDir()+"/save_matrice.txt");
		try {
    		// Flux interne
    		FileOutputStream output = MainActivity.activity.openFileOutput("save_matrice.txt", Context.MODE_PRIVATE);

    		// On �crit dans le flux interne
    		output.write((txt).getBytes());

    		if(output != null)
    			output.close();
    	} 
    	catch (FileNotFoundException e) {e.printStackTrace();} 
    	catch (IOException e) {e.printStackTrace();}
	}
	
	public static String lire_droid() {
		// Lecture
		String res="";
		mFile = new File(MainActivity.activity.getFilesDir()+"/save_matrice.txt");
	    try {
	    	FileInputStream input = MainActivity.activity.openFileInput("save_matrice.txt");
	        int value;
	        // On utilise un StringBuffer pour construire la cha�ne au fur et � mesure
	        StringBuffer lu = new StringBuffer();
	        // On lit les caract�res les uns apr�s les autres
	        while((value = input.read()) != -1) {
	        	// On �crit dans le fichier le caract�re lu
	        	lu.append((char)value);
	        }
	        if(input != null) {
	        	input.close();
	        	if(lu.toString()!=null)
	        		res=lu.toString();
	        }
	    } 
	    catch (FileNotFoundException e) {e.printStackTrace();} 
	    catch (IOException e) {e.printStackTrace();}
	    if(res==null) return "";
	    return res;
	}
	public static File mFile = null;
	
	
}
