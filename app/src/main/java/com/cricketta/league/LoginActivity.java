package com.cricketta.league;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.auth.api.*;

import REST.Model.User;
import REST.RestClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends BaseActivity implements
        View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 106;
    private Context mContext;
    private TextView tv1;
    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        SetupFacebookLogin();
        SetupGoogleLogin();
        getUserData();


        tv1 = (TextView) findViewById(R.id.title);
        Typeface face = Typeface.createFromAsset(getAssets(), "MarioNett.ttf");
        tv1.setTypeface(face);

        if (Profile.getCurrentProfile() != null) {
            isUserRegistered(mstrThirdPartyId);
        }

        if (mGoogleApiClient.isConnected() && mGoogleApiClient != null) {
            isUserRegistered(mstrThirdPartyId);
        }
    }

    private void isUserRegistered(String userId) {
        RestClient client = new RestClient();
        client.AuthService().getUserByFBId(userId, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                if (user == null) {
                    Intent intent = new Intent(mContext, create_user_activity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, Main_Activity.class);
                    saveUserData(user.getFacebookId(), user.getUserName(), user.getUserId(), user.getProfileImage());
                    startActivity(intent);
                }
                finish();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("retro", error.getMessage());
                showToast("Error validating User");
            }
        });
    }

    private void SetupFacebookLogin() {
        loginButton = (LoginButton) findViewById(R.id.login_button);


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //showToast(loginResult.getAccessToken().getUserId());
                saveUserData(loginResult.getAccessToken().getUserId(), Profile.getCurrentProfile().getName(), 0, 0);
                isUserRegistered(loginResult.getAccessToken().getUserId());
            }

            @Override
            public void onCancel() {
                showToast("Login Cancelled.");
            }

            @Override
            public void onError(FacebookException e) {
                showToast("Login attempt failed.");
            }
        });
    }

    private void SetupGoogleLogin() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("retro", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            saveUserData(acct.getId(), acct.getDisplayName(), 0, 0);
            isUserRegistered(acct.getId());
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
