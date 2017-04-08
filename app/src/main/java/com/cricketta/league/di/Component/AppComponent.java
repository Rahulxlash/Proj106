package com.cricketta.league.di.Component;

import com.cricketta.league.BaseActivity;
import com.cricketta.league.League.CreateLeaguePresenter;
import com.cricketta.league.League.LeagueListPresenter;
import com.cricketta.league.League.LeagueMatchesPresenter;
import com.cricketta.league.League.LeagueSummaryPresenter;
import com.cricketta.league.League.frag_league_list;
import com.cricketta.league.LeagueMatch.MatchTossPresenter;
import com.cricketta.league.LeagueMatch.SelectPlayerPresenter;
import com.cricketta.league.LeagueMatch.SelectTeamPresenter;
import com.cricketta.league.Login.LoginPresenter;
import com.cricketta.league.Main.MainPresenter;
import com.cricketta.league.Main.Main_Activity;
import com.cricketta.league.di.Modules.AppModule;

import javax.inject.Singleton;

import REST.Model.AuthModel;
import REST.Model.LeagueModel;
import REST.Model.MatchModel;
import dagger.Component;

/**
 * Created by rahul.sharma01 on 4/3/2017.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    //View
    void inject(BaseActivity baseActivity);

    void inject(Main_Activity main_activity);

    void inject(frag_league_list frag_league_list);

    //Presenters
    void inject(MainPresenter mainPresenter);

    void inject(LoginPresenter loginPresenter);

    void inject(LeagueListPresenter leagueListPresenter);

    void inject(LeagueSummaryPresenter leagueSummaryPresenter);

    void inject(LeagueMatchesPresenter leagueMatchesPresenter);

    void inject(CreateLeaguePresenter createLeaguePresenter);

    void inject(MatchTossPresenter matchTossPresenter);

    void inject(SelectTeamPresenter selectTeamPresenter);

    void inject(SelectPlayerPresenter selectPlayerPresenter);

    //Models
    void inject(LeagueModel leagueModel);

    void inject(AuthModel authModel);

    void inject(MatchModel matchModel);


}
