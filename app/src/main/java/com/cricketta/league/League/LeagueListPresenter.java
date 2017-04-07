package com.cricketta.league.League;

import com.cricketta.league.CricApplication;
import com.cricketta.league.ModelCallback;

import java.util.ArrayList;

import javax.inject.Inject;

import REST.Model.LeagueModel;
import REST.ViewModel.League;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by rahul.sharma01 on 4/6/2017.
 */

public class LeagueListPresenter implements LeagueContract.ListPresenter {
    @Inject
    LeagueModel leagueModel;
    LeagueContract.ListView view;
    CompositeSubscription subscriptions;
    ArrayList<League> Leagues;

    public LeagueListPresenter(LeagueContract.ListView view) {
        CricApplication.getAppComponent().inject(this);
        this.view = view;
        subscriptions = new CompositeSubscription();
    }

    @Override
    public void loadLeagues() {
        view.showProgress("Loading leagues...");
        Subscription s = leagueModel.getLeagues(new ModelCallback<ArrayList<League>>() {
            @Override
            public void onSuccess(ArrayList<League> response) {
                view.hideProgress();
                Leagues = response;
                view.showList(response);
            }

            @Override
            public void onError(String networkError) {
                view.onError(networkError);
            }
        });
        subscriptions.add(s);
    }

    @Override
    public void onLeagueClick(League league) {
        if (league.Accepted() == false && league.getIsMyLeague() == false) {
            view.showAcceptChallange(league);
        }
        if (league.Accepted() == true) {
            view.showLeagueDetails(league);
        }
    }

    @Override
    public void onStop() {
        subscriptions.unsubscribe();
    }
}
