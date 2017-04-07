package com.cricketta.league.Login;

import com.cricketta.league.BasePresenter;
import com.cricketta.league.BaseView;

/**
 * Created by rahul.sharma01 on 4/5/2017.
 */

public interface LoginContract {
    public interface Presenter extends BasePresenter {
        void Login(String UserId, String UserName, String PhotoUrl);

        void CreateUser();
    }

    public interface View extends BaseView {
        void goToMain();
    }
}
