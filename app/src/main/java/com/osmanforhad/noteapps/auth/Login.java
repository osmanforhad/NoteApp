package com.osmanforhad.noteapps.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.osmanforhad.noteapps.MainActivity;
import com.osmanforhad.noteapps.R;
import com.osmanforhad.noteapps.Splash;

public class Login extends AppCompatActivity {

    /* variable declaration **/
    EditText lEmail, lPassword;
    Button loginNow;
    TextView forgetPass, createAcc;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* enable the back button **/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* title for login screen **/
        getSupportActionBar().setTitle("Login to your Notes");

        /* initial the xml resource **/
        lEmail = findViewById(R.id.email);
        lPassword = findViewById(R.id.lPassword);
        loginNow = findViewById(R.id.loginBtn);
        forgetPass = findViewById(R.id.forgotPasword);
        createAcc = findViewById(R.id.createAccount);
        spinner = findViewById(R.id.progressBar3);


        /* get instance of firebase DB **/
        fAuth = FirebaseAuth.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();

        fStore = FirebaseFirestore.getInstance();


        showWarning();//calling the method

        /* make clickable the login button **/
        loginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* extract the user input data **/
                String mEmail = lEmail.getText().toString();
                String mPassword = lPassword.getText().toString();

                /* check the user input field are empty or not **/
                if (mEmail.isEmpty() || mPassword.isEmpty()) {
                    /* display the message **/
                    Toast.makeText(Login.this, "Fields are Required.", Toast.LENGTH_SHORT).show();
                    return;//means return the user to same screen or control flow
                }//end of the if condition

                /* make the progressbar is visible **/
                spinner.setVisibility(View.VISIBLE);

                /* delete notes and
                * check the user is anonymous or not **/
                if (fAuth.getCurrentUser().isAnonymous()) {
                    FirebaseUser user = fAuth.getCurrentUser();

                    /* delete the anonymous user created data **/
                    fStore.collection("notes").document(user.getUid()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            /* display message **/
                            Toast.makeText(Login.this,"All Temp Notes are Deleted.",Toast.LENGTH_SHORT).show();

                        }//end of the onSuccess method

                    });//end of the addOnSuccessListener

                    /* delete the anonymous user **/
                    user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            /* display message **/
                            Toast.makeText(Login.this,"Temp User Deleted.",Toast.LENGTH_SHORT).show();
                        }//end of the onSuccess method

                    });//end of the onSuccess

                }//end of the if condition

                fAuth.signInWithEmailAndPassword(mEmail, mPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        /* display message  and
                         * sent the user into main screen**/
                        Toast.makeText(Login.this, "Success !", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplication(), MainActivity.class));
                        /* for erase all the cache
                         * of the previously open activity **/
                        finish();
                    }//end of the onSuccess method

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        /* display message **/
                        Toast.makeText(Login.this, "Login Failed." + e.getMessage(), Toast.LENGTH_SHORT).show();
                        /* make the progressbar is invisible or remove **/
                        spinner.setVisibility(View.GONE);
                    }//end of the onFailure method

                });//end of the addOnFailureListener

            }//end of the onClick method

        });//end of the setOnClickListener for login button

        /* make clickable the create account button **/
        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* send the user into registration screen **/
                startActivity(new Intent(getApplicationContext(), Register.class));
            }//end of the onClick method

        });//end of the setOnClickListener for create account button

    }//end of the onCreate method

    /* for display the warning message **/
    private void showWarning() {
        AlertDialog.Builder warning = new AlertDialog.Builder(this)
                .setTitle("Are you sure ?")
                .setMessage("Linking the Existing Account Will delete all the temp notes. Create new account to save them.")
                .setPositiveButton("Save Note", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /* sent anonymous user to register screen **/
                        startActivity(new Intent(getApplicationContext(), Register.class));
                        /*finish the activity **/
                        finish();
                    }//end of the onClick method

                }).setNegativeButton("Its ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        
                    }//end of the onClick method

                });//end of the setNegativeButton

        /* display warning to the user **/
        warning.show();

    }//end of the showWarning method

}//end of the class
