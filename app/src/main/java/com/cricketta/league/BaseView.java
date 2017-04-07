package com.cricketta.league;

/**
 * Created by rahul.sharma01 on 4/4/2017.
 */

public interface BaseView {
    void showProgress(String message);

    void hideProgress();

    void onError(String message);
}
