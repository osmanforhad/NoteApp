package com.osmanforhad.noteapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    /* variable Declaration **/
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView nav_view;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* initial xml through the variable UI **/
        drawerLayout = findViewById(R.id.drawer);
        nav_view = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
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

    }//end of the onCreate method

    /* method for handle the click of menu button **/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        /* check which item is click
        * for Display the message or open the new screen **/
        switch (item.getItemId()){
            default:
                //for display default message
                Toast.makeText(this,"Coming soon.",Toast.LENGTH_SHORT).show();
        }


        return false;
    }//end of the onNavigationItemSelected method

}//end of the class
