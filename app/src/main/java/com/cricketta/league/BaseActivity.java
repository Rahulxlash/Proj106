package com.cricketta.league;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.cricketta.league.Utils.CricApplication;
import com.cricketta.league.fragment.Request_Toss_dlg;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rahul.sharma01 on 3/7/2017.
 */

public class BaseActivity extends AppCompatActivity {
    public static final String MyPref = "MY_PREF";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_ID = "USER_ID";
    public static final String FACEBOOK_ID = "FACEBOOK_ID";
    public static final String PROFILE_IMAGE = "PROFILE_IMAGE";
    public static final String PHOTO_URL = "PHOTO_URL";
    public static final String FIREBASE_TOKEN = "FirebaseToken";
    public int mintUserId;
    public String mstrUserName;
    public String mstrThirdPartyId;
    public int ProfileImage;
    public String mstrPhotoUrl;
    public ProgressDialog mProgressDialog;
    public String mFirebaseToken;
    private FragmentManager fragManager;

    public void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog = new ProgressDialog(this);
        fragManager = getSupportFragmentManager();
    }

    public void saveUserData(String ThirdPartyId, String UserName, int UserId, int ProfileImage, String photoUrl) {
        SharedPreferences sharedpreferences = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(FACEBOOK_ID, ThirdPartyId);
        editor.putInt(USER_ID, UserId);
        editor.putString(USER_NAME, UserName);
        editor.putInt(PROFILE_IMAGE, ProfileImage);
        editor.putString(PHOTO_URL, photoUrl);
        editor.apply();
    }

    public void getUserData() {
        SharedPreferences mySharedpreprence = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        mstrThirdPartyId = mySharedpreprence.getString(FACEBOOK_ID, "");
        mintUserId = mySharedpreprence.getInt(USER_ID, 0);
        mstrUserName = mySharedpreprence.getString(USER_NAME, "");
        ProfileImage = mySharedpreprence.getInt(PROFILE_IMAGE, 0);
        mstrPhotoUrl = mySharedpreprence.getString(PHOTO_URL, "");
    }

    public void showDialog(String message) {

        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.setMessage(message);
            mProgressDialog.show();
        }
    }

    public void hideDialog() {

        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            //mProgressDialog.setMessage("");
            mProgressDialog.dismiss();
        }
    }

    public String getDeviceToken() {
        SharedPreferences mySharedpreprence = PreferenceManager.getDefaultSharedPreferences(this);
        mFirebaseToken = mySharedpreprence.getString(FIREBASE_TOKEN, "");
        return mFirebaseToken;
    }

    @Override
    protected void onResume() {
        super.onResume();
        CricApplication.activityResumed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        CricApplication.activityPaused();
    }

    public void handleNotification(Object obj) {
        try {
            Bundle bundle;

            if (obj instanceof String) {
                JSONObject requestData = new JSONObject((String) obj);
                bundle = new Bundle();
                for (int i = 0; i < requestData.names().length(); i++) {
                    bundle.putString(requestData.names().getString(i), requestData.getString(requestData.names().getString(i)));
                }
            } else {
                bundle = (Bundle) obj;
            }

            switch (bundle.getString("Tag")) {
                case "TOSS_REQUEST":
                    Request_Toss_dlg frag = new Request_Toss_dlg();
                    frag.setArguments(bundle);
                    frag.show(fragManager, "Toss");
                    break;

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
