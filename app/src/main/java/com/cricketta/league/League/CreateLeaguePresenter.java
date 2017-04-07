package com.cricketta.league.League;

import com.cricketta.league.CricApplication;
import com.cricketta.league.ModelCallback;

import javax.inject.Inject;

import REST.Model.LeagueModel;
import REST.ViewModel.League;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by rahul.sharma01 on 4/7/2017.
 */

public class CreateLeaguePresenter implements LeagueContract.CreateLeaguePresenter {
    @Inject
    LeagueModel leagueModel;

    CompositeSubscription subscriptions;
    LeagueContract.CreateLeagueView view;

    public CreateLeaguePresenter(LeagueContract.CreateLeagueView view) {
        CricApplication.getAppComponent().inject(this);
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    @Override
    public void CreateLeague(String name, String competitor) {
        view.showProgress("Creating new League...");
        Subscription s = leagueModel.createLeague(name, competitor, new ModelCallback<League>() {
            @Override
            public void onSuccess(League response) {
                view.hideProgress();
                view.leagueCreated(response);
            }

            @Override
            public void onError(String networkError) {
                view.hideProgress();
                view.onError(networkError);
            }
        });
    }

    @Override
    public void onStop() {

    }
}
