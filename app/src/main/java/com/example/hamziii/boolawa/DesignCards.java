package com.example.hamziii.boolawa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

public class DesignCards extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth.AuthStateListener mAuthListner;
    private FirebaseAuth maAuth;
    private ImageView first_image;
    private ImageView second_image;
    private ImageView third_image;
    private ImageView fourth_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_cards);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        maAuth = FirebaseAuth.getInstance();
        mAuthListner = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                if (firebaseAuth.getCurrentUser()==null)
                {
                    Intent intent = new Intent(DesignCards.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        };



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //image view


        first_image=(ImageView) findViewById(R.id.first_image);
        String url= "https://firebasestorage.googleapis.com/v0/b/boolawa-a7fc3.appspot.com/o/IMG-20160106-WA0010.jpg?alt=media&token=45744c02-85cf-474e-a27b-272230e15f2b";
        Glide.with(getApplicationContext()).load(url).into(first_image);

        second_image=(ImageView) findViewById(R.id.second_image);
        String url2= "https://firebasestorage.googleapis.com/v0/b/boolawa-a7fc3.appspot.com/o/IMG-20160106-WA0021.jpg?alt=media&token=fd702ede-a7b1-458a-943b-eb389b299dc5";
        Glide.with(getApplicationContext()).load(url2).into(second_image);


        third_image=(ImageView) findViewById(R.id.third_image);
        String url3= "https://firebasestorage.googleapis.com/v0/b/boolawa-a7fc3.appspot.com/o/IMG-20160106-WA0009.jpg?alt=media&token=1fce2f54-bf73-45bd-ab34-65e27f79623f";
        Glide.with(getApplicationContext()).load(url3).into(third_image);

        fourth_image=(ImageView) findViewById(R.id.fourth_image);
        String url4= "https://firebasestorage.googleapis.com/v0/b/boolawa-a7fc3.appspot.com/o/download.jpg?alt=media&token=c3597eec-563c-4f4c-a3da-616e56684a55";
        Glide.with(getApplicationContext()).load(url4).into(fourth_image);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        switch(id){
            case R.id.first_item:
                Intent h = new Intent(DesignCards.this,gallery.class);
                startActivity(h);
                break;
            case R.id.second_item:
                Intent n = new Intent(DesignCards.this,tools.class);
                startActivity(n);
                break;
            case R.id.third_item:
                Intent z = new Intent(DesignCards.this,slideshow.class);
                startActivity(z);
                break;
            case R.id.signout:
                maAuth.signOut();
                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        maAuth.addAuthStateListener(mAuthListner);
    }
}
