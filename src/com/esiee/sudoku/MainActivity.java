/*
 * Application développée par
 * MARTEL Andy
 * MERCANDALLI Jonathan
 * disponible en Anglais, Français et Allemand.
 * 
 */

package com.esiee.sudoku;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

	private static MainView view;
	public static MainActivity activity;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;
		view = new MainView(this); 
		
		/* Charge les valeurs de la grille */
		lib.charger();
		
		/* Charge les valeurs de la grille */
	    setContentView(view);
	    
	    // Mode plein ecran Fullscrenn ou Immesive (pour Android 4.4)
	    if (Build.VERSION.SDK_INT >= 11) {
		    int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
		    int newUiOptions = uiOptions;
		    if (Build.VERSION.SDK_INT >= 14)
		        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
		    if (Build.VERSION.SDK_INT >= 16)
		    	newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
		    if (Build.VERSION.SDK_INT >= 18)
		        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
		    getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
	    }
	}

	@Override
    protected void onPause() {
        super.onPause();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
    }

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
    }
    
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		
		if (Build.VERSION.SDK_INT >= 11) {
			if (hasFocus) {
				getWindow().getDecorView().setSystemUiVisibility(
							View.SYSTEM_UI_FLAG_LAYOUT_STABLE
							| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
							| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_FULLSCREEN
							| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
			}
		}
	}
	
    
    /* A PROPOS */
    @Override
	public boolean onOptionsItemSelected(MenuItem item) { 
	    switch (item.getItemId()) { 
	    case R.id.action_about: 
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this); 
			builder.setMessage( "Andy Martel\nJonathan Mercandalli" ) 
					 .setTitle( "SudoESIEE 2014" )
					 .setCancelable(false) 
				     .setPositiveButton("Retour", new DialogInterface.OnClickListener() { 
				         public void onClick(DialogInterface dialog, int id) { 
				             dialog.cancel();		
			                
			          } 
			       }); 
			AlertDialog alert = builder.create();	
	        alert.show(); 
	        return true; 
	    } 
	    return false; 
	}
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
    
    public MainView getView() {return view;}
    public MainView getview() {return view;}
}