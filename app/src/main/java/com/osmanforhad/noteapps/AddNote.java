package com.osmanforhad.noteapps;

import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class AddNote extends AppCompatActivity {

    /* variable declaration **/
    FirebaseFirestore fStore;
    EditText noteTitle, noteContent;

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
                }

                //save note
                DocumentReference docRef = fStore.collection("notes").document();
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
                    }
                });//end of the addOnFailureListener

            }//end of the onClick method

        });//end of the setOnClickListener

    }//end of the onCreate method

}//end of the class
