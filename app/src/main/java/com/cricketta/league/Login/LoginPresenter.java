package com.cricketta.league.Login;

import com.cricketta.league.CricApplication;
import com.cricketta.league.ModelCallback;

import javax.inject.Inject;

import REST.Model.AuthModel;
import REST.Service.AuthService;
import REST.ViewModel.User;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by rahul.sharma01 on 4/5/2017.
 */

public class LoginPresenter implements LoginContract.Presenter {
    @Inject
    AuthModel authModel;
    @Inject
    AuthService authService;

    LoginContract.View view;
    CompositeSubscription subscriptions;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
        CricApplication.getAppComponent().inject(this);
        this.subscriptions = new CompositeSubscription();
    }


    @Override
    public void Login(final String FBId, final String userName, String PhotoUrl) {
        view.showProgress("Logging in....");
        authModel.LoginUser(FBId, userName, PhotoUrl, new ModelCallback<User>() {
            @Override
            public void onSuccess(User response) {
                view.hideProgress();
                view.goToMain();
            }

            @Override
            public void onError(String networkError) {
                view.hideProgress();
                view.onError(networkError);
            }
        });

    }

    @Override
    public void CreateUser() {

    }

    @Override
    public void onStop() {

    }
}
