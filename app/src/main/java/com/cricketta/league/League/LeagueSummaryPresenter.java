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

public class LeagueSummaryPresenter implements LeagueContract.SummaryPresenter {

    @Inject
    LeagueModel leagueModel;
    CompositeSubscription subscriptions;
    LeagueContract.SummaryView view;

    public LeagueSummaryPresenter(LeagueContract.SummaryView view) {
        CricApplication.getAppComponent().inject(this);
        this.view = view;
        subscriptions = new CompositeSubscription();
    }

    @Override
    public void loadSummaryMatches(int leagueId) {
        view.showProgress("Loading....");
        Subscription s = leagueModel.getLeagueSummary(leagueId, new ModelCallback<League>() {
            @Override
            public void onSuccess(League response) {
                view.hideProgress();
                view.showSummary(response);
            }

            @Override
            public void onError(String networkError) {
                view.hideProgress();
                view.onError(networkError);
            }
        });
        subscriptions.add(s);
    }

    @Override
    public void onStop() {
        subscriptions.unsubscribe();
    }
}
