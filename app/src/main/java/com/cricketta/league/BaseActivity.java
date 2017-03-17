package com.cricketta.league;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

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
    public int mintUserId;
    public String mstrUserName;
    public String mstrThirdPartyId;
    public int ProfileImage;
    public String mstrPhotoUrl;
    public ProgressDialog mProgressDialog;

    public void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
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
            mProgressDialog.setMessage("");
            mProgressDialog.dismiss();
        }
    }
}
