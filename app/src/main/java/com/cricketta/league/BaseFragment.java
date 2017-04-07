package com.cricketta.league;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Created by rahul.sharma01 on 4/6/2017.
 */

public class BaseFragment extends Fragment implements BaseView {
    public ProgressDialog mProgressDialog;

    @Override
    public void showProgress(String message) {

        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.setMessage(message);
            mProgressDialog.show();
        }
    }

    public void hideProgress() {

        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void onError(String message) {
        Log.d("retro", message);
    }

}
