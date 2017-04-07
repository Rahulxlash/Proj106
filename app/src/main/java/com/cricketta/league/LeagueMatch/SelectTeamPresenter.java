package com.cricketta.league.LeagueMatch;

import com.cricketta.league.CricApplication;
import com.cricketta.league.ModelCallback;

import java.util.ArrayList;

import javax.inject.Inject;

import REST.Model.MatchModel;
import REST.ViewModel.Player;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by rahul.sharma01 on 4/7/2017.
 */

public class SelectTeamPresenter implements MatchContract.TeamSelectPresenter {
    private final SelectTeam_frag view;
    @Inject
    MatchModel matchModel;
    CompositeSubscription subscriptions;
    ArrayList<Player> players;

    SelectTeamPresenter(SelectTeam_frag view) {
        CricApplication.getAppComponent().inject(this);
        this.view = view;
        subscriptions = new CompositeSubscription();
    }

    @Override
    public void onStop() {

    }

    @Override
    public void addPlayer(int matchId, int playerId) {

    }

    @Override
    public void onAddClick() {
        view.showPlayerList();
    }

    @Override
    public void getTeam(int matchId) {

    }

    @Override
    public void getAllPlayers(int matchId) {
        view.showProgress("Loading players....");
        subscriptions.add(matchModel.getAllPlayers(matchId, new ModelCallback<ArrayList<Player>>() {
            @Override
            public void onSuccess(ArrayList<Player> response) {
                view.hideProgress();
                players = response;
            }

            @Override
            public void onError(String networkError) {
                view.hideProgress();
                view.onError(networkError);
            }
        }));
    }
}
