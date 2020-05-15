package com.osmanforhad.noteapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Splash extends AppCompatActivity {

    /* variable declaration **/
    private static int SCREEN_SPLASH_TIME = 2000; /* means 2 Second **/
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /* create instance of firebase auth **/
        fAuth = FirebaseAuth.getInstance();

        /* for sending splash screen to main screen after the 2 sec **/
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /* check the user is already
                 * loggedIn or not **/
                if (fAuth.getCurrentUser() != null) {
                    /* send the user into main content screen **/
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    //create new anonymous account
                    fAuth.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            /* Display message **/
                            Toast.makeText(Splash.this,"Logged in With Temporary Account.",Toast.LENGTH_SHORT).show();
                            /* send the user into main content screen **/
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }//end of the onSuccess method

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            /* Display message **/
                            Toast.makeText(Splash.this,"Error ! " + e.getMessage(),Toast.LENGTH_SHORT).show();
                        }//end of the onFailure method

                    });//end of the signInAnonymously

                }//end of the else statement

            }//end of the run method

        }, SCREEN_SPLASH_TIME/* calling the variable **/);//end of the Runnable method

    }//end of the onCreate method

}//end of the class
