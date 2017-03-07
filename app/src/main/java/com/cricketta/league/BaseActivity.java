package com.cricketta.league;


import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by rahul.sharma01 on 3/7/2017.
 */

public class BaseActivity extends AppCompatActivity {
    private static final String USER_NAME = "USER_NAME";
    private static final String USER_ID = "USER_ID";
    private static final String FACEBOOK_ID = "FACEBOOK_ID";
    private static final String PROFILE_IMAGE = "PROFILE_IMAGE";


    public void showToast(String message)
    {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
