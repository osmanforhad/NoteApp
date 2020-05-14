package com.osmanforhad.noteapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.osmanforhad.noteapps.model.Adapter;
import com.osmanforhad.noteapps.model.Note;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /* variable Declaration **/
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView nav_view;
    Toolbar toolbar;
    RecyclerView noteLists;
    Adapter adapter;
    FirebaseFirestore fStore;
    FirestoreRecyclerAdapter<Note, NoteViewHolder> noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* initial xml through the variable UI **/
        drawerLayout = findViewById(R.id.drawer);
        nav_view = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.noteDetailsTitle);
        noteLists = findViewById(R.id.noteList);

        setSupportActionBar(toolbar);//for setup the toolbar


        /* get the instance of fireStore **/
        fStore = FirebaseFirestore.getInstance();

        /* Query the Database form the notes collection
         *  here collection works as like
         * MYSQL DB Table**/
        Query NoteQuery = fStore.collection("notes").orderBy("title", Query.Direction.DESCENDING);

        /* execute the query object **/
        FirestoreRecyclerOptions<Note> allNotes = new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(NoteQuery, Note.class)
                .build();

        /* object for noteAdapter **/
        noteAdapter = new FirestoreRecyclerAdapter<Note, NoteViewHolder>(allNotes) {
            @Override
            protected void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, int i, @NonNull final Note note) {

                /* for receive data from main activity or list
                 * as item position **/
                noteViewHolder.noteTitle.setText(note.getTitle());
                noteViewHolder.noteContent.setText(note.getContent());

                /*  store multiple color in final variable**/
                final int colors = getRandomColor();

                /* set background color for note **/
                noteViewHolder.mCardView.setCardBackgroundColor(noteViewHolder.view.getResources().getColor(colors, null));

                /* for setup id for every note **/
                final String docId = noteAdapter.getSnapshots().getSnapshot(i).getId();


                /* for display message when click on the particular item **/
                noteViewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        /* for go to Next screen **/
                        Intent GoNext = new Intent(v.getContext(), NoteDetails.class);

                        /* for passing data with it's color in to next screen
                         * as item position**/
                        GoNext.putExtra("title", note.getTitle());//here "title" use as a key for passing specific title
                        GoNext.putExtra("content", note.getContent());//here "content" use as a key for passing specific content
                        GoNext.putExtra("color", colors);//here color use as key for passing specific color
                        GoNext.putExtra("noteId",docId);//here noteId use as key for passing specific data

                        /* for open the next screen **/
                        v.getContext().startActivity(GoNext);

                    }//end of the onClick method

                });//end of the setOnClickListener

                /* working with
                *menu icon
                * Image **/
                                    /* initial the xml Ui **/
                ImageView menuIcon = noteViewHolder.view.findViewById(R.id.menuIcon);

                /* make clickable the menu icon **/
                menuIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /* display message **/
                        Toast.makeText(MainActivity.this,"Clicked.",Toast.LENGTH_SHORT).show();

                    }//end of the onClick method

                });//end of the setOnClickListener


            }//end of the onBindViewHolder method

            @NonNull
            @Override
            public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_view_layout, parent, false);
                return new NoteViewHolder(view);
            }//end of the onCreateViewHolder method

        };//end of the FireStoreRecyclerAdapter

        /* set the navigation view **/
        nav_view.setNavigationItemSelectedListener(this);

        /* create object for actionbar toggle **/
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);

        /* set the drawer listener for drawer layout **/
        drawerLayout.addDrawerListener(toggle);

        /* enable toggle indicator **/
        toggle.setDrawerIndicatorEnabled(true);

        /* for sync the state or
         *indicate the navigation is
         * open or close currently **/
        toggle.syncState();

        /* set the layout manager for recyclerView**/
        noteLists.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        /* set Adapter for note list **/
        noteLists.setAdapter(noteAdapter);


        /* handle the add new floating button click **/
        FloatingActionButton plusIcon = findViewById(R.id.addNoteFloat);

        plusIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /* for go to next screen **/
                startActivity(new Intent(view.getContext(), AddNote.class));

            }//end of the onClick

        });//end of the setOnClickListener


    }//end of the onCreate method

    /* method for handle the click of menu button **/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        /* check which item is click
         * ans go to the screen as clicked item position **/
        switch (item.getItemId()) {
            case R.id.addNote:
                /* for go to next screen **/
                startActivity(new Intent(this, AddNote.class));
                break;

            default:
                //for display default message
                Toast.makeText(this, "Coming soon.", Toast.LENGTH_SHORT).show();
        }


        return false;
    }//end of the onNavigationItemSelected method

    /* implement the setting option menu **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        /* initial the xml layout file
         * for option menu**/
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);

        return super.onCreateOptionsMenu(menu);

    }//end of the onCreateOptionsMenu method

    /* method for handle the click of menu button **/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        /* check the button is click
         * on the option menu**/
        if (item.getItemId() == R.id.settings) {
            //for display the message
            Toast.makeText(this, "Settings Menu is Clicked.", Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);

    }//end of the onOptionsItemSelected method

    /* sub class **/
    public class NoteViewHolder extends RecyclerView.ViewHolder {

        /* variable Declaration **/
        TextView noteTitle, noteContent;
        View view;
        CardView mCardView;

        /* constructor method **/
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            /* initial the xml UI as item **/
            noteTitle = itemView.findViewById(R.id.titles);
            noteContent = itemView.findViewById(R.id.content);
            mCardView = itemView.findViewById(R.id.noteCard);

            /* for handle the click on the recycleView Items  **/
            view = itemView;

        }//end of the constructor method

    }//end of the sub class


    /* method for generate random color **/
    private int getRandomColor() {

        /* for hold the color code as integer **/
        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.blue);
        colorCode.add(R.color.yellow);
        colorCode.add(R.color.skyblue);
        colorCode.add(R.color.lightPurple);
        colorCode.add(R.color.lightGreen);
        colorCode.add(R.color.gray);
        colorCode.add(R.color.pink);
        colorCode.add(R.color.red);
        colorCode.add(R.color.lightGreen);
        colorCode.add(R.color.notgreen);

        Random randomColor = new Random();

        int number = randomColor.nextInt(colorCode.size());

        return colorCode.get(number);

    }//end of the getRandomColor method

    /* override onStart method for
     * make sure to Database operation to any change
     * the application is start**/
    @Override
    protected void onStart() {
        super.onStart();
        noteAdapter.startListening();
    }//end of the onStart method

    /* override onStop method for
     * make sure to Database operation to stop any change
     * the application is already closed**/
    @Override
    protected void onStop() {
        super.onStop();
        noteAdapter.stopListening();
    }//end of the onStop method

}//end of the main class
