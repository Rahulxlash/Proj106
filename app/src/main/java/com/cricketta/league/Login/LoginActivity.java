package com.cricketta.league.Login;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.cricketta.league.BaseActivity;
import com.cricketta.league.Main.Main_Activity;
import com.cricketta.league.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends BaseActivity implements LoginContract.View {

    LoginPresenter presenter;
    @InjectView(R.id.title)
    TextView tv1;
    @InjectView(R.id.login_button)
    LoginButton loginButton;


    private CallbackManager callbackManager;
    private Bundle notBundle;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callbackManager = CallbackManager.Factory.create();
        presenter = new LoginPresenter(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        SetupFacebookLogin();

        Typeface face = Typeface.createFromAsset(getAssets(), "waltographUI.ttf");
        tv1.setTypeface(face);
    }

    private void SetupFacebookLogin() {
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
                            presenter.Login(Profile.getCurrentProfile().getId(), Profile.getCurrentProfile().getName(), Profile.getCurrentProfile().getProfilePictureUri(200, 200).toString());
                        }
                    }
                };
                profileTracker.startTracking();
                if (Profile.getCurrentProfile() != null)
                    presenter.Login(loginResult.getAccessToken().getUserId(), Profile.getCurrentProfile().getName(), Profile.getCurrentProfile().getProfilePictureUri(200, 200).toString());
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


    @Override
    public void goToMain() {
        Intent intent = new Intent(this, Main_Activity.class);
        startActivity(intent);
        finish();
    }
}
