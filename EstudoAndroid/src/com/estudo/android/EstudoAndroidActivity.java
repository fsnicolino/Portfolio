////////////////////////////////////////////////////////
// Portfólio: Estudo Android						  // 
// Fernando Sarra Nicolino                            //
// e-mail: fsnicolino@gmail.com                       //
// skype: fsnicolino                                  // 
// telefone para contato: 11 99409-2988               //
////////////////////////////////////////////////////////
package com.estudo.android;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class EstudoAndroidActivity extends Activity  {
    /** Called when the activity is first created. */
	private Game1 game1;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        // Verbose output, adb logcat (eclipse: window -> show view -> other -> android -> logcat)
    	Log.v( this.getClass().getName(), "public void onCreate(Bundle savedInstanceState)" );
    	
        // App runs in fullscreen mode (no layout titlebar, no status bar)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN ); 
        
     
        
        game1 = new Game1(this);
        setContentView( game1 );
    }
	
	 public void onDestroy()
	    {
	    	super.onDestroy();	    	
	    }
	    
	    @Override
	public void onPause()
	    {
	    	super.onPause();
			game1.SetPause(true);
	    }
	
}