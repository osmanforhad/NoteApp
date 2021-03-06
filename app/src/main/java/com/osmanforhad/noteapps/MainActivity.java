package com.osmanforhad.noteapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.osmanforhad.noteapps.auth.Login;
import com.osmanforhad.noteapps.auth.Register;
import com.osmanforhad.noteapps.model.Adapter;
import com.osmanforhad.noteapps.model.Note;
import com.osmanforhad.noteapps.note.AddNote;
import com.osmanforhad.noteapps.note.EditNote;
import com.osmanforhad.noteapps.note.NoteDetails;

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
    FirebaseAuth fAuth;
    FirebaseUser user;

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

        /* initialize the firebase auth and firebase user **/
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();

        /* Query the Database form the notes collection
         *  here collection works as like
         * MYSQL DB Table**/ // query notes > uid > myNotes
        Query NoteQuery = fStore.collection("notes").document(user.getUid()).collection("myNotes").orderBy("title", Query.Direction.DESCENDING);


        /* execute the query object **/
        FirestoreRecyclerOptions<Note> allNotes = new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(NoteQuery, Note.class)
                .build();

        /* object for noteAdapter **/
        noteAdapter = new FirestoreRecyclerAdapter<Note, NoteViewHolder>(allNotes) {
            @Override
            protected void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, final int i, @NonNull final Note note) {

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
                        GoNext.putExtra("noteId", docId);//here noteId use as key for passing specific data

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
                    public void onClick(final View v) {

                        /* create popup menu **/
                        PopupMenu popMenu = new PopupMenu(v.getContext(), v);

                        /* set Gravity for popup menu item **/
                        popMenu.setGravity(Gravity.END);

                        /* for catch the note id
                         * as user clicked item**/
                        final String docId = noteAdapter.getSnapshots().getSnapshot(i).getId();

                        /* add Edit menu item
                         *inside this popup menu
                         * and make this menu item clickable**/
                        popMenu.getMenu().add("Edit").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                /* sent user into Edit Screen **/
                                Intent GoNext = new Intent(v.getContext(), EditNote.class);

                                /* passed the data as item clicked **/
                                GoNext.putExtra("title", note.getTitle());//here "title" use as a key for passing data as item click
                                GoNext.putExtra("content", note.getContent());//here "content" use as a key for passing data as item click
                                GoNext.putExtra("noteId", docId);//here "noteId" use as a key for passing data as item click

                                /* open the next screen **/
                                startActivity(GoNext);
                                return false;

                            }//end of the onMenuItemClick method

                        });//end of the setOnMenuItemClickListener

                        /* add Delete menu item
                         *inside this popup menu
                         * and make this menu item clickable**/
                        popMenu.getMenu().add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                /* for delete item **/
                                DocumentReference DocRef = fStore.collection("notes").document(user.getUid()).collection("myNotes").document(docId);//here docId use as key for receive specific data
                                DocRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(MainActivity.this, "One Note Deleted Successfully.", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(MainActivity.this, "Error in Deleting Note.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return false;

                            }//end of the onMenuItemClick method

                        });//end of the setOnMenuItemClickListener

                        /* Show the popup message **/
                        popMenu.show();

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

        /* for header view **/
        View headerView = nav_view.getHeaderView(0);
        //initial the xml id
        TextView username = headerView.findViewById(R.id.userDisplayName);
        TextView userEmail = headerView.findViewById(R.id.userDisplayEmail);
        /* check the user is anonymous or not **/
        if (user.isAnonymous()) {
            userEmail.setVisibility(View.GONE);
            username.setText("Temporary User");
        } else {
            /* retrieve and display the user info from db **/
            userEmail.setText(user.getEmail());
            username.setText(user.getDisplayName());
        }


        /* handle the add new floating button click **/
        FloatingActionButton plusIcon = findViewById(R.id.addNoteFloat);

        plusIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /* for go to next screen **/
                startActivity(new Intent(view.getContext(), AddNote.class));
                /* for slide effect
                 * when go to one screen to another **/
                overridePendingTransition(R.anim.slide_up,R.anim.slide_down);
                /* for erase all the cache
                 * of the previously open activity **/
                finish();

            }//end of the onClick

        });//end of the setOnClickListener


    }//end of the onCreate method

    /* method for handle the click of menu button **/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        /* after click the menu item
         *for close the navigation drawer **/
        drawerLayout.closeDrawer(GravityCompat.START);

        /* check which item is click
         * ans go to the screen as clicked item position **/
        switch (item.getItemId()) {
            case R.id.addNote:
                /* for go to next screen **/
                startActivity(new Intent(this, AddNote.class));
                /* for slide effect
                 * when go to one screen to another **/
                overridePendingTransition(R.anim.slide_up,R.anim.slide_down);
                break;

            case R.id.sync:
                /* check the user is anonymous or not **/
                if (user.isAnonymous()) {
                    /* got ot the register screen **/
                    startActivity(new Intent(this, Login.class));
                    /* for slide effect
                     * when go to one screen to another **/
                    overridePendingTransition(R.anim.slide_up,R.anim.slide_down);
                } else {
                    /* Display the message **/
                    Toast.makeText(this, "You are already Connected.", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.logout:
                checkUser(); //calling the method
                break;

            default:
                //for display default message
                Toast.makeText(this, "Coming soon.", Toast.LENGTH_SHORT).show();
        }


        return false;
    }//end of the onNavigationItemSelected method

    /* method check the use is logged in with
     * anonymous account or Real account **/
    private void checkUser() {
        /* the user is Anonymous
         * then display an alert **/
        if (user.isAnonymous()) {
            displayAlert();//calling the method
        } else {
            /* the user is real go to sign out
             * and set to Splash screen **/
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), Splash.class));//for goto splash screen
            /* for slide effect
             * when go to one screen to another **/
            overridePendingTransition(R.anim.slide_up,R.anim.slide_down);


        }//end of the else statement

    }//end of the checkUser method

    private void displayAlert() {
        AlertDialog.Builder warning = new AlertDialog.Builder(this)
                .setTitle("Are you sure ?")
                .setMessage("You are logged in with Temporary Account. Logging out will Delete All the notes.")
                .setPositiveButton("Sync Note", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /* sent anonymous user to register screen **/
                        startActivity(new Intent(getApplicationContext(), Register.class));
                        /* for slide effect
                         * when go to one screen to another **/
                        overridePendingTransition(R.anim.slide_up,R.anim.slide_down);
                        /* for erase all the cache
                         * of the previously open activity **/
                        finish();
                    }//end of the onClick method

                }).setNegativeButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO: delete all the notes created by the current anonymous user

                        //TODO: delete the current anonymous user
                        user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                /* after delete redirect ot splash screen **/
                                startActivity(new Intent(getApplicationContext(), Splash.class));
                                /* for slide effect
                                 * when go to one screen to another **/
                                overridePendingTransition(R.anim.slide_up,R.anim.slide_down);
                                /* for erase all the cache
                                 * of the previously open activity **/
                                finish();;
                            }
                        });
                    }//end of the onClick method

                });//end of the setNegativeButton

        /* display warning to the user **/
        warning.show();

    }//end of the displayAlert method

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
