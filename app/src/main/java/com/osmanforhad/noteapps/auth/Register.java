package com.osmanforhad.noteapps.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.osmanforhad.noteapps.MainActivity;
import com.osmanforhad.noteapps.R;

public class Register extends AppCompatActivity {

    /* variable declaration **/
    EditText rUserName, rUserEmail, rUserPass, rUserConfPass;
    Button syncAccount;
    TextView loginAct;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /* title for register screen **/
        getSupportActionBar().setTitle("Connect to NoteApps");

        /* Enable the back button **/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* bind the view by using
         * initial xml id **/
        rUserName = findViewById(R.id.userName);
        rUserEmail = findViewById(R.id.userEmail);
        rUserPass = findViewById(R.id.password);
        rUserConfPass = findViewById(R.id.passwordConfirm);
        syncAccount = findViewById(R.id.createAccount);
        loginAct = findViewById(R.id.login);
        progressBar = findViewById(R.id.progressBar4);

        /* get firebase instance **/
        fAuth = FirebaseAuth.getInstance();

        /* make login button clickable **/
        loginAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* go to login screen **/
                startActivity(new Intent(getApplicationContext(), Login.class));
            }//end of the onClick method

        });//end of the setOnClickListener

        /* make clickable the sync account button **/
        syncAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* extract the anonymous user data **/
                String uUsername = rUserName.getText().toString();
                String uUserEmail = rUserEmail.getText().toString();
                String uUserPass = rUserPass.getText().toString();
                String uConfPass = rUserConfPass.getText().toString();

                /* check the  register user field is empty or not**/
                if (uUserEmail.isEmpty() || uUsername.isEmpty() || uUserPass.isEmpty() || uConfPass.isEmpty()) {
                    /* Display the message **/
                    Toast.makeText(Register.this, "All Fields are Required", Toast.LENGTH_SHORT).show();
                    return;//means return the control flow
                }

                /* check Password and ConfirmPassword is same or not **/
                if (!uUserPass.equals(uConfPass)) {
                    /* set the error **/
                    rUserConfPass.setError("Password Do not Match.");
                }

                /* make the progressbar is visible **/
                progressBar.setVisibility(View.VISIBLE);

                /* if previous both condition are true
                 * it will proceed the anonymous account to real account
                 * or New Account **/
                AuthCredential credential = EmailAuthProvider.getCredential(uUserEmail, uUserPass);
                fAuth.getCurrentUser().linkWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        /* display the message **/
                        Toast.makeText(Register.this, "Notes are Synced.", Toast.LENGTH_SHORT).show();
                        /* sent user into the main screen **/
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }//end of the onSuccess method

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        /* display the message **/
                        Toast.makeText(Register.this, "Failed to Connect. Tray Again.", Toast.LENGTH_SHORT).show();
                        /* make the progressbar is invisible or remove **/
                        progressBar.setVisibility(View.GONE);
                    }//end of the onFailure method

                });//end of the addOnFailureListener


            }//end of the onClick method

        });//end of the setOnClickListener


    }//end of the onCreate method

    /* make clickable the back button **/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        /* send the user into main screen **/
        startActivity(new Intent(this, MainActivity.class));
        /* for erase all the cache
         * of the previously open activity **/
        finish();
        return super.onOptionsItemSelected(item);
    }

}//end of the class
