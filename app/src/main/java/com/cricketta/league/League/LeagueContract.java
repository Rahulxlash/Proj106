package com.cricketta.league.League;

import com.cricketta.league.BasePresenter;
import com.cricketta.league.BaseView;

import java.util.ArrayList;

import REST.ViewModel.League;
import REST.ViewModel.LeagueMatch;

/**
 * Created by rahul.sharma01 on 4/6/2017.
 */

public interface LeagueContract {

    public interface CreateLeaguePresenter extends BasePresenter {
        void CreateLeague(String name, String competitor);
    }

    public interface CreateLeagueView extends BaseView {
        void leagueCreated(League league);
    }

    public interface ListPresenter extends BasePresenter {
        void loadLeagues();

        void onLeagueClick(League league);
    }

    public interface ListView extends BaseView {
        void showList(ArrayList<League> leagues);

        void showLeagueDetails(League league);

        void showAcceptChallange(League league);
    }

    public interface SummaryPresenter extends BasePresenter {
        void loadSummaryMatches(int leagueId);
    }

    public interface SummaryView extends BaseView {
        void showSummary(League league);
    }

    public interface MatchListPresenter extends BasePresenter {
        void loadLeagueMatches(int LeagueId);

        void onMatchClick(LeagueMatch match);
    }

    public interface MatchListView extends BaseView {
        void showMatches(ArrayList<LeagueMatch> matches);

        void showSelectPlayer(LeagueMatch match);

        void showToss(LeagueMatch match);

        void showScoreCard(LeagueMatch match);
    }

}
