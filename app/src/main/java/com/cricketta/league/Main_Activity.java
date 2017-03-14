package com.cricketta.league;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import android.widget.TextView;

import com.facebook.login.LoginManager;

public class Main_Activity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TextView user_information;
    private ImageView ImgVwProfileImage;
    private String UserName, ProfileImage, FacebookId;
    private int User_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setImageResource(R.drawable.plus_outline);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getPreferences();
        setProfileImage(navigationView.getHeaderView(0));
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
        getMenuInflater().inflate(R.menu.main_, menu);
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

        if (id == R.id.home) {
            // Handle the camera action
        } else if (id == R.id.my_league) {

        } else if (id == R.id.competitor) {

        } else if (id == R.id.analysis) {

        } else if (id == R.id.uptournament) {

        } else if (id == R.id.settings) {

        } else if (id == R.id.logout) {
            LoginManager.getInstance().logOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getPreferences() {
        SharedPreferences mySharedpreprence = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        FacebookId = mySharedpreprence.getString(FACEBOOK_ID, "");
        User_Id = mySharedpreprence.getInt(USER_ID, 0);
        UserName = mySharedpreprence.getString(USER_NAME, "");
        ProfileImage = mySharedpreprence.getString(PROFILE_IMAGE, "");
    }

    private void setProfileImage(View view) {
        user_information = (TextView) view.findViewById(R.id.txtUserName);
        user_information.setText(UserName);

        ImgVwProfileImage = (ImageView) view.findViewById(R.id.profileImage);

        switch (ProfileImage) {
            case "0":
                ImgVwProfileImage.setImageResource(R.drawable.prof_ico_1);
                break;
            case "1":
                ImgVwProfileImage.setImageResource(R.drawable.prof_ico_2);
                break;
            case "2":
                ImgVwProfileImage.setImageResource(R.drawable.prof_ico_3);
                break;
            case "3":
                ImgVwProfileImage.setImageResource(R.drawable.prof_ico_4);
                break;
            case "4":
                ImgVwProfileImage.setImageResource(R.drawable.prof_ico_5);
                break;
            case "5":
                ImgVwProfileImage.setImageResource(R.drawable.prof_ico_6);
                break;
        }
    }
}

