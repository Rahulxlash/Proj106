package com.cricketta.league.Main;

import android.net.Uri;

import com.cricketta.league.BasePresenter;
import com.cricketta.league.BaseView;

/**
 * Created by rahul.sharma01 on 4/4/2017.
 */

public interface MainContract {
    public interface Presenter extends BasePresenter {
        void checkLoginStatus();

        void Logout();
    }

    public interface View extends BaseView {
        void goToLogin();

        void showSnackBar();

        void showNotification();

        void showLeagueList();

        void setProfileImage(Uri url, String UserName);
    }
}
