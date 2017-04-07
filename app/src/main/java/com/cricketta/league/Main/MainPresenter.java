package com.cricketta.league.Main;

import android.content.SharedPreferences;
import android.net.Uri;

import com.cricketta.league.CricApplication;
import com.cricketta.league.ModelCallback;

import javax.inject.Inject;

import REST.Model.AuthModel;
import REST.ViewModel.User;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by rahul.sharma01 on 4/4/2017.
 */

public class MainPresenter implements MainContract.Presenter {

    MainContract.View view;
    @Inject
    SharedPreferences sharedPrefrences;
    @Inject
    AuthModel authModel;

    private CompositeSubscription subscriptions;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        CricApplication.getAppComponent().inject(this);
        this.subscriptions = new CompositeSubscription();
    }

    @Override
    public void checkLoginStatus() {
        if (!authModel.isUserLogin()) {
            view.goToLogin();
        } else {
            view.setProfileImage(Uri.parse(authModel.PhotoUrl), authModel.UserName);
            view.showLeagueList();
        }
    }

    @Override
    public void Logout() {
        view.showProgress("Logging out...");
        authModel.Logout(new ModelCallback<User>() {
            @Override
            public void onSuccess(User response) {
                view.hideProgress();
                view.goToLogin();
            }

            @Override
            public void onError(String message) {
                view.hideProgress();
                view.onError(message);
            }
        });
    }

    @Override
    public void onStop() {
        subscriptions.unsubscribe();
    }
}
