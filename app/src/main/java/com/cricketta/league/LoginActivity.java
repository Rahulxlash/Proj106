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

public class LoginActivity extends AppCompatActivity {
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
        tv1=(TextView)findViewById(R.id.title);
        Typeface face= Typeface.createFromAsset(getAssets(), "MarioNett.ttf");
        tv1.setTypeface(face);
        if (Profile.getCurrentProfile() == null) {
            loginButton = (LoginButton) findViewById(R.id.login_button);

            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Toast toast = Toast.makeText(mContext, loginResult.getAccessToken().getUserId(), Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(mContext, create_user_activity.class);
                    startActivity(intent);
                }

                @Override
                public void onCancel() {
//                info.setText("Login attempt canceled.");
                    Toast toast = Toast.makeText(mContext, "Login cancelled by user.", Toast.LENGTH_SHORT);
                    toast.show();
                }

                @Override
                public void onError(FacebookException e) {
//                info.setText("Login attempt failed.");
                    Toast toast = Toast.makeText(mContext, "Error in logging in to the application.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        } else {
            Intent intent = new Intent(mContext, create_user_activity.class);
            startActivity(intent);
        }
    }
}
