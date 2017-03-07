package com.cricketta.league;


import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by rahul.sharma01 on 3/7/2017.
 */

public class BaseActivity extends AppCompatActivity {
    public void showToast(String message)
    {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
