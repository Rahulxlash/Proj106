package com.cricketta.league.LeagueMatch;

import com.cricketta.league.BasePresenter;
import com.cricketta.league.BaseView;

import REST.ViewModel.ScoreCard;
import REST.ViewModel.Toss;

/**
 * Created by rahul.sharma01 on 4/7/2017.
 */

public interface MatchContract {
    public interface TossPresenter extends BasePresenter {
        void RequestToss(int matchId);

        void doToss(int matchId, int tossOption);
    }

    public interface TossView extends BaseView {
        void tossSubmitted();

        void tossDone(Toss toss);
    }

    public interface TeamSelectPresenter extends BasePresenter {
        void addPlayer(int matchId, int playerId);

        void onAddClick();

        void getTeam(int matchId);

        void getAllPlayers(int matchId);
    }

    public interface TeamSelectView extends BaseView {
        void showPlayerList();

        void showTeam();
    }

    public interface PlayerSelectPresenter extends BasePresenter {
        void selectPlayer(int PlayerId, int MatchId);
    }

    public interface PlayerSelectView extends BaseView {
        void closeView(ScoreCard card);

    }
}
