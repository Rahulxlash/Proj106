package com.cricketta.league.LeagueMatch;

import com.cricketta.league.CricApplication;
import com.cricketta.league.ModelCallback;

import javax.inject.Inject;

import REST.Model.MatchModel;
import REST.ViewModel.LeagueMatch;
import REST.ViewModel.Toss;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by rahul.sharma01 on 4/7/2017.
 */

public class MatchTossPresenter implements MatchContract.TossPresenter {

    @Inject
    MatchModel matchModel;

    CompositeSubscription subscription;
    MatchContract.TossView view;

    public MatchTossPresenter(MatchContract.TossView view) {
        this.view = view;
        CricApplication.getAppComponent().inject(this);
        subscription = new CompositeSubscription();
    }

    @Override
    public void RequestToss(int matchId) {
        view.showProgress("Sending Request..");
        subscription.add(matchModel.requestToss(matchId, new ModelCallback<LeagueMatch>() {
            @Override
            public void onSuccess(LeagueMatch response) {
                view.hideProgress();
                view.tossSubmitted();
            }

            @Override
            public void onError(String networkError) {
                view.onError(networkError);
            }
        }));
    }

    @Override
    public void doToss(int matchId, int tossOption) {
        subscription.add(matchModel.doToss(matchId, tossOption, new ModelCallback<Toss>() {
            @Override
            public void onSuccess(Toss response) {
                view.hideProgress();
                view.tossDone(response);
            }

            @Override
            public void onError(String networkError) {
                view.onError(networkError);
            }
        }));
    }

    @Override
    public void onStop() {
        subscription.unsubscribe();
    }
}
