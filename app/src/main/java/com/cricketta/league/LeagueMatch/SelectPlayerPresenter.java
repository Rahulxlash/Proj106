package com.cricketta.league.LeagueMatch;

import com.cricketta.league.CricApplication;
import com.cricketta.league.ModelCallback;
import com.cricketta.league.events.PlayerSelectedEvent;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import REST.Model.AuthModel;
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
        //EventBus.getDefault().register(this);
    }

    @Override
    public void selectPlayer(final int PlayerId, final int MatchId) {
        subscription.add(matchModel.addPlayer(MatchId, PlayerId, new ModelCallback<ScoreCard>() {
            @Override
            public void onSuccess(ScoreCard response) {
                EventBus.getDefault().post(new PlayerSelectedEvent(PlayerId, MatchId, 0, AuthModel.UserId));
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
        //EventBus.getDefault().unregister(this);
    }
}
