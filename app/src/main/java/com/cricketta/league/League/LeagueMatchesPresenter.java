package com.cricketta.league.League;

import com.cricketta.league.CricApplication;
import com.cricketta.league.ModelCallback;

import java.util.ArrayList;

import javax.inject.Inject;

import REST.Model.LeagueModel;
import REST.ViewModel.LeagueMatch;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by rahul.sharma01 on 4/7/2017.
 */

public class LeagueMatchesPresenter implements LeagueContract.MatchListPresenter {
    @Inject
    LeagueModel leagueModel;

    CompositeSubscription subscriptions;
    LeagueContract.MatchListView view;

    public LeagueMatchesPresenter(LeagueContract.MatchListView view) {
        CricApplication.getAppComponent().inject(this);
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void loadLeagueMatches(int LeagueId) {
        view.showProgress("Loading....");
        Subscription s = leagueModel.getLeagueMatches(LeagueId, new ModelCallback<ArrayList<LeagueMatch>>() {
            @Override
            public void onSuccess(ArrayList<LeagueMatch> response) {
                view.hideProgress();
                view.showMatches(response);
            }

            @Override
            public void onError(String networkError) {
                view.onError(networkError);
            }
        });
        subscriptions.add(s);
    }

    @Override
    public void onMatchClick(LeagueMatch match) {
        if (match.tossDone == true && match.teamSelected == false) {
            view.showSelectPlayer(match);
        } else if (match.tossDone == false) {
            view.showToss(match);
        } else {
            view.showScoreCard(match);
        }

    }

    @Override
    public void onStop() {
        subscriptions.unsubscribe();
    }
}
