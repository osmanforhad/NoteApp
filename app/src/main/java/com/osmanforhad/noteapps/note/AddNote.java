package com.osmanforhad.noteapps.note;

import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.osmanforhad.noteapps.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class AddNote extends AppCompatActivity {

    /* variable declaration **/
    FirebaseFirestore fStore;
    EditText noteTitle, noteContent;
    ProgressBar progressBarSave;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* get the instance of firebase fire store **/
        fStore = FirebaseFirestore.getInstance();

        /* initial xml UI **/
        noteContent = findViewById(R.id.addNoteContent);
        noteTitle = findViewById(R.id.addNoteTitle);
        progressBarSave = findViewById(R.id.progressBar);

        /* for get current user **/
        user = FirebaseAuth.getInstance().getCurrentUser();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* extract the string **/
                String nTitle = noteTitle.getText().toString();
                String nContent = noteContent.getText().toString();

                /* check the user input is empty or not **/
                if(nTitle.isEmpty() || nContent.isEmpty()){

                  /* for display message to user **/
                    Toast.makeText(AddNote.this,"Note Can not Save With Empty Field.",Toast.LENGTH_SHORT).show();
                    return; //its men return user to the same screen
                }//end of the if condition

                /* make progressbar visible **/
                progressBarSave.setVisibility(View.VISIBLE);

                //save note
                DocumentReference docRef = fStore.collection("notes").document(user.getUid()).collection("myNotes").document();
                Map<String,Object> note = new HashMap<>();
                note.put("title",nTitle);
                note.put("content",nContent);

                docRef.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        /* display message **/
                        Toast.makeText(AddNote.this,"Note Added.",Toast.LENGTH_SHORT).show();

                        /* redirect user to the main screen **/
                        onBackPressed();
                    }//end of the onSuccess method

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        /* display message **/
                        Toast.makeText(AddNote.this,"Error Try again.",Toast.LENGTH_SHORT).show();
                        progressBarSave.setVisibility(View.VISIBLE);
                    }
                });//end of the addOnFailureListener

            }//end of the onClick method

        });//end of the setOnClickListener

        /* setup top actionbar **/
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }//end of the onCreate method

    /* for display close icon
    * on toolbar
    * need to override onCreate option menu **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* inflate the menu **/
        MenuInflater inflater = getMenuInflater();
        /* inflate the layout **/
        inflater.inflate(R.menu.close_menu,menu);

        return super.onCreateOptionsMenu(menu);

    }//end of the onCreateOptionsMenu method

    /* to handle the click on menu option
    * need to override onOption item selected **/

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        /* checked the close button is clicked or not **/
        if(item.getItemId() == R.id.close){

            /* for display message **/
            Toast.makeText(this,"Not Saved.",Toast.LENGTH_SHORT).show();

            /* for go to back **/
            onBackPressed();

        }//end of the if condition

        return super.onOptionsItemSelected(item);

    }//end of the onOptionsItemSelected method

}//end of the class
