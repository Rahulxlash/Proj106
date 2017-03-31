package com.cricketta.league;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cricketta.league.fragment.SelectCompetitor_frag;
import com.cricketta.league.fragment.SelectTeam_frag;
import com.cricketta.league.fragment.frag_league_list;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Picasso;

import REST.Model.User;
import REST.RestClient;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Main_Activity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.OnConnectionFailedListener {
    public ActionBar actionBar;
    private TextView user_information;
    private ImageView ImgVwProfileImage;
    private FragmentManager fragmentManager;
    private FloatingActionButton fab;

    @Override
    protected void onStart() {
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
//        Common.mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();
//        Common.mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setImageResource(R.drawable.plus_outline);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                SelectTeam_frag frag = new SelectTeam_frag();
                showFragment(frag, "SelectTeam", true);

            }
        });


        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment fr = fragmentManager.findFragmentById(R.id.fragment_frag_league_list);
                if (fr != null) {
                    Log.e("fragment=", fr.getClass().getSimpleName());
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
        if (savedInstanceState == null) {
            //do your stuff
            frag_league_list fragLeagueList = new frag_league_list();
            showFragment(fragLeagueList, "Home", false);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        showDialog("Loading");
        getUserData();
        setProfileImage(navigationView.getHeaderView(0));
        hideDialog();

        String obj = getIntent().getStringExtra("notData");
        if (obj != null) {
            handleNotification(obj);
        }
        if (getIntent().getExtras() != null)
            handleNotification(getIntent().getExtras());
    }



    public void showFragment(Fragment fragment, String tag, boolean addToStack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
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
            if (ProfileImage == 0)
                SignOutFaacebook();
            else
                signOutGoogle();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void SignOutFaacebook() {
        LoginManager.getInstance().logOut();
        UnRegisterDevicetoUser(mintUserId);
        Intent intent = new Intent(Main_Activity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void signOutGoogle() {

        Auth.GoogleSignInApi.signOut(Common.mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        //Common.mGoogleApiClient.disconnect();
                        Intent intent = new Intent(Main_Activity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    private void setProfileImage(View view) {
        ImgVwProfileImage = (ImageView) view.findViewById(R.id.profileImage);
        if (ProfileImage == 0) {//Facebook Id
            Uri imageUri = Profile.getCurrentProfile().getProfilePictureUri(200, 200);
            Picasso.with(this).load(imageUri).transform(new CropCircleTransformation()).into(ImgVwProfileImage);
        } else { //Google Id
            if (mstrPhotoUrl != "")
                Picasso.with(this).load(mstrPhotoUrl).transform(new CropCircleTransformation()).into(ImgVwProfileImage);
        }
        user_information = (TextView) view.findViewById(R.id.txtUserName);
        user_information.setText(mstrUserName.trim());
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);

    }

    private void UnRegisterDevicetoUser(int UserId) {
        RestClient client = new RestClient();
        client.AuthService().UnRegisterDevice(UserId, getDeviceToken(), new Callback<User>() {
            @Override
            public void success(User user, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}


