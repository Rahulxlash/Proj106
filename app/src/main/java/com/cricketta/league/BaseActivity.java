package com.cricketta.league;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.cricketta.league.fragment.Request_Toss_dlg;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rahul.sharma01 on 3/7/2017.
 */

public class BaseActivity extends AppCompatActivity implements BaseView {
    public ProgressDialog mProgressDialog;
    private FragmentManager fragManager;

    public void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CricApplication.getAppComponent().inject(this);
        mProgressDialog = new ProgressDialog(this);
        fragManager = getSupportFragmentManager();
    }

    public void showProgress(String message) {

        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.setMessage(message);
            mProgressDialog.show();
        }
    }

    public void hideProgress() {

        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            //mProgressDialog.setMessage("");
            mProgressDialog.dismiss();
        }
    }

    public void onError(String message) {
        Log.d("retro", message);
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
            if (!bundle.keySet().contains("Tag"))
                return;
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
