package com.example.project.boolawa;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private FirebaseAuth mAuth ;
    private FirebaseAuth.AuthStateListener mAuthListner ;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    private DrawerLayout mdrawerlayout;
    private ActionBarDrawerToggle mtoogle;
    private Toolbar mtoolbar , toolbar;
    private AppBarLayout appBarLayout;
    private TabLayout tab;
    private ViewPager.OnPageChangeListener indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();



        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() == null) {
                    Intent LoginIntent = new Intent(Home.this , LoginActivity.class);
                    LoginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(LoginIntent);
                }

            }
        };



        tab = (TabLayout) findViewById(R.id.tabs);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Boolawa");

        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        /*TabLayout.Tab tab = tabLayout.getTabAt(Integer.parseInt(getIntent().getStringExtra("fragmentId")));
        tab.select();*/

        int defaultInt = 0 ;
        int page = getIntent().getIntExtra("One", defaultInt);
        mViewPager.setCurrentItem(page);

        mdrawerlayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mtoogle = new ActionBarDrawerToggle(this , mdrawerlayout , R.string.open , R.string.close);

        mdrawerlayout.addDrawerListener(mtoogle);
        mtoogle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        appBarLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext() , R.color.colorPrimaryDark));
        toolbar.setBackgroundColor(ContextCompat.getColor(getApplicationContext() , R.color.colorPrimary));

    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        /*if (id == R.id.action_settings) {
            return true;
        }*/

        if(mtoogle.onOptionsItemSelected(item))
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayselectedscreen (int id)
    {
        switch (id)
        {

            case R.id.nav_allUsers:
                Intent intent = new Intent(Home.this, AllUsers.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.nav_friends:
                Intent intent5 = new Intent(Home.this, Friends.class);
                intent5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent5);
                break;
            case R.id.nav_userEvents:
                Intent intent6 = new Intent(Home.this, UserCreatedInvitations.class);
                intent6.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent6);
                break;
            case R.id.nav_exit:
                mAuth.getInstance().signOut();

                Intent intent8 = new Intent(Home.this , LoginActivity.class);
                intent8.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent8);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        displayselectedscreen(id);
        return false;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you want to logout ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    mAuth.getInstance().signOut();

                    Intent intent = new Intent(Home.this , LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();

                }
            });

            final AlertDialog ad = builder.create();
            ad.show();


        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getItemPosition(Object object) {

            return super.getItemPosition(object);
        }

        @Override
        public Fragment getItem(int position) {
            //return PlaceholderFragment.newInstance(position + 1);

            switch (position) {
                case 0:
                    FragmentDesignCard designcard = new FragmentDesignCard();
                    return designcard;
                case 1:
                    FragmentSendInvitation sendInvitation = new FragmentSendInvitation();
                    return  sendInvitation;
                case 2:
                    FragmentViewInvitation viewInvitation = new FragmentViewInvitation();
                    return  viewInvitation;
                case 3:
                    FragmentHire hire = new FragmentHire();
                    return hire;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 5 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Design Card";
                case 1:
                    return "Send Invitation";
                case 2:
                    return "View Invitation";
                case 3:
                    return "Hire";
            }
            return null;
        }
    }
}
