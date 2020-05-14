package com.osmanforhad.noteapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class Splash extends AppCompatActivity {

    /* variable declaration **/
    private static int SCREEN_SPLASH_TIME = 2000; /* means 2 Second **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /* for sending splash screen to main screen after the 2 sec **/
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /* for go to the main Content screen**/
                startActivity(new Intent(getApplicationContext(),MainActivity.class));

            }//end of the run method

        },SCREEN_SPLASH_TIME/* calling the variable **/);//end of the Runnable method

    }//end of the onCreate method

}//end of the class
