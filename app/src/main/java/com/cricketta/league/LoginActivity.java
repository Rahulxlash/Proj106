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
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.auth.api.*;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

import java.util.Arrays;

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
        Typeface face = Typeface.createFromAsset(getAssets(), "waltographUI.ttf");
        tv1.setTypeface(face);

        if (Profile.getCurrentProfile() != null) {
            isUserRegistered(mstrThirdPartyId);
        }

        OptionalPendingResult<GoogleSignInResult> pendingResult = Auth.GoogleSignInApi.silentSignIn(Common.mGoogleApiClient);
        if (pendingResult != null) {
            handleGooglePendingResult(pendingResult);
        } else {
            //no result from silent login. Possibly display the login page again
        }
    }

    private void isUserRegistered(String userId) {
        RestClient client = new RestClient();
        showDialog("Loading");
        client.AuthService().getUserByFBId(userId.trim(), new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                if (user == null) {
                    getUserData();
                    RestClient client = new RestClient();
                    client.AuthService().createUser(mstrUserName, mstrThirdPartyId, ProfileImage, new Callback<User>() {
                        @Override
                        public void success(User user, Response response) {
                            hideDialog();
                            saveUserData(user.getFacebookId(), user.getUserName(), user.getUserId(), user.getProfileImage(), mstrPhotoUrl);
                            Intent intent = new Intent(mContext, Main_Activity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            hideDialog();
                            Log.d("Retro", error.getMessage());
                            showToast("Error Registering User.");
                        }
                    });
                } else {
                    hideDialog();
                    Intent intent = new Intent(mContext, Main_Activity.class);
                    saveUserData(user.getFacebookId(), user.getUserName(), user.getUserId(), user.getProfileImage(), mstrPhotoUrl);
                    startActivity(intent);
                }
                finish();
            }

            @Override
            public void failure(RetrofitError error) {
                hideDialog();
                Log.e("retro", error.getMessage());
                showToast("Error validating User");
            }
        });
    }

    private void SetupFacebookLogin() {
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("user_friends"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                ProfileTracker profileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                        this.stopTracking();
                        Profile.setCurrentProfile(currentProfile);
                        if (currentProfile != null) {
                            String strId = Profile.getCurrentProfile().getId();
                            showToast(strId);
                            saveUserData(strId, Profile.getCurrentProfile().getName(), 0, 0, "");
                            isUserRegistered(strId);
                        }
                    }
                };
                profileTracker.startTracking();
                //showToast(loginResult.getAccessToken().getUserId());

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
                .requestProfile()
                .build();
        Common.mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
    }

    private void handleGooglePendingResult(OptionalPendingResult<GoogleSignInResult> pendingResult) {
        if (pendingResult.isDone()) {
            // There's immediate result available.
            GoogleSignInResult signInResult = pendingResult.get();
            onSilentSignInCompleted(signInResult);
        } else {
            // There's no immediate result ready,  waits for the async callback.
            pendingResult.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult signInResult) {
                    onSilentSignInCompleted(signInResult);
                }
            });
        }
    }

    private void onSilentSignInCompleted(GoogleSignInResult signInResult) {
        GoogleSignInAccount signInAccount = signInResult.getSignInAccount();
        if (signInAccount != null) {
            // you have a valid sign in account. Skip the login.
            isUserRegistered(mstrThirdPartyId);
        } else {
            // you don't have a valid sign in account. Eventually display the login page again
        }
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
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(Common.mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("retro", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            String mstrPhtUrl;
            if (acct.getPhotoUrl() == null)
                mstrPhtUrl = "";
            else
                mstrPhtUrl = acct.getPhotoUrl().toString();
            saveUserData(acct.getId(), acct.getDisplayName(), 0, 1, mstrPhtUrl);
            isUserRegistered(acct.getId());
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
