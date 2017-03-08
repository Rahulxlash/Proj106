package com.cricketta.league;


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


    public void showToast(String message)
    {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
