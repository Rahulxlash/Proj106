package com.cricketta.league;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import REST.Model.User;
import REST.RestClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends BaseActivity {
    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    Context mContext;
    TextView tv1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
        tv1 = (TextView) findViewById(R.id.title);
        Typeface face = Typeface.createFromAsset(getAssets(), "MarioNett.ttf");
        tv1.setTypeface(face);
        if (Profile.getCurrentProfile() == null) {
            loginButton = (LoginButton) findViewById(R.id.login_button);

            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    showToast(loginResult.getAccessToken().getUserId());
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
        } else {
            Log.d("retroUID",Profile.getCurrentProfile().getId());
            isUserRegistered(Profile.getCurrentProfile().getId());
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
                    startActivity(intent);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                showToast("Error validating User");
            }
        });
    }
}
