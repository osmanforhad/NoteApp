package com.osmanforhad.noteapps;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class NoteDetails extends AppCompatActivity {

    /* variable Declaration **/
    TextView title, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* for back button **/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

         title = (TextView) findViewById(R.id.noteDetailsTitle);
         content = (TextView) findViewById(R.id.noteDetailsContent);
         /* enable scrolling for large text **/
        content.setMovementMethod(new ScrollingMovementMethod());

        /* get data from previous screen sending by item click
        * as position **/
        Intent userNote = getIntent();

        title.setText(userNote.getStringExtra("title"));//here "title" use as a key which was define in previous screen
        content.setText(userNote.getStringExtra("content"));//here "content" use as a key which was define in previous screen

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }//end of the onClick method

        });//end of the setOnClickListener

    }//end of the onCreate method

    /*back button as item click**/

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        /* identify back button is clicked as item **/
        if (item.getItemId() == android.R.id.home){

            /* for sent the user back **/
            onBackPressed();

        }//end of the if condition

        return super.onOptionsItemSelected(item);

    }//end of the onOptionsItemSelected method

}//end of the class
