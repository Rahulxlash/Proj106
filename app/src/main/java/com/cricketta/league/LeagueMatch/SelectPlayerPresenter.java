package com.cricketta.league.LeagueMatch;

import com.cricketta.league.CricApplication;
import com.cricketta.league.ModelCallback;

import javax.inject.Inject;

import REST.Model.MatchModel;
import REST.ViewModel.ScoreCard;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by rahul.sharma01 on 4/8/2017.
 */

public class SelectPlayerPresenter implements MatchContract.PlayerSelectPresenter {
    @Inject
    MatchModel matchModel;

    CompositeSubscription subscription;
    MatchContract.PlayerSelectView view;

    public SelectPlayerPresenter(MatchContract.PlayerSelectView view) {
        this.view = view;
        CricApplication.getAppComponent().inject(this);
        subscription = new CompositeSubscription();
    }

    @Override
    public void selectPlayer(int PlayerId, int MatchId) {
        subscription.add(matchModel.addPlayer(MatchId, PlayerId, new ModelCallback<ScoreCard>() {
            @Override
            public void onSuccess(ScoreCard response) {
                view.closeView(response);
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
