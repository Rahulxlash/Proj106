package com.cricketta.league.League;

import com.cricketta.league.CricApplication;
import com.cricketta.league.ModelCallback;

import javax.inject.Inject;

import REST.Model.LeagueModel;
import REST.ViewModel.League;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by rahul.sharma01 on 4/11/2017.
 */

public class LeagueChallangePresenter implements LeagueContract.ChallangePresenter {
    @Inject
    LeagueModel leagueModel;
    CompositeSubscription subscriptions;
    LeagueContract.ChallangeView view;

    public LeagueChallangePresenter(LeagueContract.ChallangeView view) {
        this.view = view;
        CricApplication.getAppComponent().inject(this);
        subscriptions = new CompositeSubscription();
    }


    @Override
    public void AccpetChallange(int LeagueId) {
        view.showProgress("Updating league");
        Subscription s = leagueModel.AcceptLeague(LeagueId, new ModelCallback<League>() {
                    @Override
                    public void onSuccess(League response) {
                        view.hideProgress();
                        view.hideView();
                    }

                    @Override
                    public void onError(String networkError) {
                        view.hideProgress();
                        view.onError(networkError);
                    }
                }
        );
        subscriptions.add(s);
    }

    @Override
    public void RejectChallange(int LeagueId) {
        view.showProgress("Updating league");
        Subscription s = leagueModel.RejectLeague(LeagueId, new ModelCallback<League>() {
                    @Override
                    public void onSuccess(League response) {
                        view.hideProgress();
                        view.hideView();
                    }

                    @Override
                    public void onError(String networkError) {
                        view.hideProgress();
                        view.onError(networkError);
                    }
                }
        );
        subscriptions.add(s);
    }

    @Override
    public void onStop() {
        subscriptions.unsubscribe();
    }
}
