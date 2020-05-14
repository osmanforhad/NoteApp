package com.osmanforhad.noteapps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

public class EditNote extends AppCompatActivity {

    /* global variable **/
    Intent userNote;

    /* variable declaration **/
    EditText editNoteTitle, editNoteContent;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        /* custom toolbar setup **/
        Toolbar toolbar = findViewById(R.id.toolbar);

        /* actionbar setup **/
        setSupportActionBar(toolbar);

        /* get the instance of firebaseFieStore Database **/
        fStore = fStore.getInstance();

        /* receive data as item click **/
        userNote = getIntent();

        /* initial the xml UI **/
        editNoteTitle = findViewById(R.id.editNoteTitle);
        editNoteContent = findViewById(R.id.editNoteContent);

        /* received data from previous screen **/
        String noteTitle = userNote.getStringExtra("title");//here "title" use as a key for receive the data as item clicked
        String noteContent = userNote.getStringExtra("content");//here "content" use as a key for receive the data as item clicked

        /* set previous screen data into edit box **/
        editNoteTitle.setText(noteTitle);
        editNoteContent.setText(noteContent);

    }//end of the onCreate method

}//end of the class
