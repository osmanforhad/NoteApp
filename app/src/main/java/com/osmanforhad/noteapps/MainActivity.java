package com.osmanforhad.noteapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.osmanforhad.noteapps.model.Adapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    /* variable Declaration **/
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView nav_view;
    Toolbar toolbar;
    RecyclerView noteLists;
    Adapter adapter;

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

        /* set the navigation view **/
        nav_view.setNavigationItemSelectedListener(this);

        /* create object for actionbar toggle **/
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);

        /* set the drawer listener for drawer layout **/
        drawerLayout.addDrawerListener(toggle);

        /* enable toggle indicator **/
        toggle.setDrawerIndicatorEnabled(true);

        /* for sync the state or
        *indicate the navigation is
        * open or close currently **/
        toggle.syncState();


        /* for demo Note Data **/
        List<String> titles = new ArrayList<>();
        List<String> content = new ArrayList<>();

        titles.add("First Note Title");
        content.add("First Note Content sample");

        titles.add("Second Note Title");
        content.add("Lorem ipsum, or lipsum as it is sometimes known, is dummy text used in" +
                " laying out print, graphic or web designs. The passage is attributed to an " +
                "unknown typesetter in the 15th century who is thought to have scrambled parts" +
                " of Cicero's De Finibus Bonorum et Malorum for use in a type specimen book.");

        titles.add("Third Note Title");
        content.add("Third Note Content sample");

        /* making object for adapter **/
        adapter = new Adapter(titles,content);

        /* set the layout manager for recyclerView**/
        noteLists.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        /* set Adapter for note list **/
        noteLists.setAdapter(adapter);


        /* handle the add new floating button click **/
        FloatingActionButton plusIcon = findViewById(R.id.addNoteFloat);

        plusIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /* for go to next screen **/
                startActivity(new Intent(view.getContext(),AddNote.class));

            }//end of the onClick

        });//end of the setOnClickListener


    }//end of the onCreate method

    /* method for handle the click of menu button **/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        /* check which item is click
        * ans go to the screen as clicked item position **/
        switch (item.getItemId()){
            case R.id.addNote:
                /* for go to next screen **/
                startActivity(new Intent(this,AddNote.class));
                break;

            default:
                //for display default message
                Toast.makeText(this,"Coming soon.",Toast.LENGTH_SHORT).show();
        }


        return false;
    }//end of the onNavigationItemSelected method

    /* implement the setting option menu **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        /* initial the xml layout file
        * for option menu**/
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu,menu);

        return super.onCreateOptionsMenu(menu);

    }//end of the onCreateOptionsMenu method

    /* method for handle the click of menu button **/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        /* check the button is click
         * on the option menu**/
        if (item.getItemId() == R.id.settings){
            //for display the message
            Toast.makeText(this,"Settings Menu is Clicked.",Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);

    }//end of the onOptionsItemSelected method

}//end of the class
