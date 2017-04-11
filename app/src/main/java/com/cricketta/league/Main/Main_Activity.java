package com.cricketta.league.Main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cricketta.league.BaseActivity;
import com.cricketta.league.CricApplication;
import com.cricketta.league.League.SelectCompetitor_frag;
import com.cricketta.league.League.frag_league_list;
import com.cricketta.league.Login.LoginActivity;
import com.cricketta.league.R;
import com.cricketta.league.events.LoginEvent;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class Main_Activity extends BaseActivity
        implements MainContract.View, NavigationView.OnNavigationItemSelectedListener {
    public ActionBar actionBar;
    //@Inject
    MainPresenter presenter;
    @Inject
    EventBus eventBus;
    @Inject
    Picasso picasso;
    @InjectView(R.id.fab)
    FloatingActionButton fab;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.drawer_layout)
    DrawerLayout drawer;
    @InjectView(R.id.nav_view)
    NavigationView navigationView;
    private TextView user_information;
    private ImageView ImgVwProfileImage;
    private FragmentManager fragmentManager;

    @Override
    protected void onStart() {
        super.onStart();
        CricApplication.getAppComponent().inject(this);
        if (!eventBus.getDefault().isRegistered(this)) {
            eventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_);
        ButterKnife.inject(this);
        presenter = new MainPresenter(this);


        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreateLeague();
            }
        });

        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment fr = fragmentManager.findFragmentById(R.id.fragment_frag_league_list);
                if (fr != null) {
                    fab.setVisibility(View.INVISIBLE);
                    switch (fr.getClass().getSimpleName()) {
                        case "frag_league_list":
                            setActionBarTitle("Home");
                            fab.setVisibility(View.VISIBLE);
                            break;
                        case "SelectCompetitor_frag":
                            setActionBarTitle("Create League");
                            break;
                        default:
                            setActionBarTitle("Cricketta");
                    }
                }
            }
        });
//        if (savedInstanceState == null) {
//            showLeagueList();
//        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        presenter.checkLoginStatus();

//        String obj = getIntent().getStringExtra("notData");
//        if (obj != null) {
//            handleNotification(obj);
//        }
//        if (getIntent().getExtras() != null)
//            handleNotification(getIntent().getExtras());
    }


    public void showFragment(Fragment fragment, String tag, boolean addToStack, boolean replace) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (replace)
            transaction.replace(R.id.fragment_frag_league_list, fragment, tag);
        else
            transaction.add(R.id.fragment_frag_league_list, fragment, tag);
        if (addToStack)
            transaction.addToBackStack(tag);
        transaction.commit();
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
            presenter.Logout();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setProfileImage(Uri imageUri, String UserName) {
        View view = navigationView.getHeaderView(0);
        ImgVwProfileImage = (ImageView) view.findViewById(R.id.profileImage);
        //Uri imageUri = Profile.getCurrentProfile().getProfilePictureUri(200, 200);
        Picasso.with(this).load(imageUri).transform(new CropCircleTransformation()).into(ImgVwProfileImage);
        user_information = (TextView) view.findViewById(R.id.txtUserName);
        user_information.setText(UserName);
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);

    }

    @Override
    public void goToLogin() {
        Intent intent = new Intent(Main_Activity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showLeagueList() {
        frag_league_list fragLeagueList = new frag_league_list();
        showFragment(fragLeagueList, "Home", false, false);
    }

    @Override
    public void showSnackBar() {

    }

    @Override
    public void showNotification() {

    }

    private void openCreateLeague() {
        SelectCompetitor_frag frag = new SelectCompetitor_frag();
        showFragment(frag, "CreateLeague", true, false);
    }

    @Subscribe
    public void onMessageEvent(LoginEvent event) {/* Do something */}

}


