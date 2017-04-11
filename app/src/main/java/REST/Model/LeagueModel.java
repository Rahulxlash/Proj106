package REST.Model;

import android.content.SharedPreferences;
import android.util.Log;

import com.cricketta.league.CricApplication;
import com.cricketta.league.ModelCallback;

import java.util.ArrayList;

import javax.inject.Inject;

import REST.Service.LeagueService;
import REST.ViewModel.League;
import REST.ViewModel.LeagueMatch;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by rahul.sharma01 on 4/4/2017.
 */

public class LeagueModel {
    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    AuthModel authModel;
    @Inject
    LeagueService leagueService;

    public LeagueModel() {
        CricApplication.getAppComponent().inject(this);
    }

    public Subscription createLeague(String name, String competitor, final ModelCallback<League> callback) {
        return leagueService.createLeague(name, AuthModel.UserId, competitor)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<League>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(League league) {
                        callback.onSuccess(league);
                    }
                });
    }

    public Subscription getLeagues(final ModelCallback<ArrayList<League>> callback) {
        int userId = AuthModel.UserId;
        return leagueService.getUserLeague(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<League>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(ArrayList<League> leagues) {
                        callback.onSuccess(leagues);
                    }
                });
    }

    public Subscription getLeagueSummary(int LeagueId, final ModelCallback<League> callback) {
        int userId = AuthModel.UserId;
        return leagueService.getLeagueSummary(LeagueId, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<League>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("retro", e.getMessage());
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(League league) {
                        callback.onSuccess(league);
                    }
                });
    }

    public Subscription getLeagueMatches(int LeagueId, final ModelCallback<ArrayList<LeagueMatch>> callback) {
        int userId = AuthModel.UserId;
        return leagueService.getLeagueMatches(LeagueId, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<LeagueMatch>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("retro", e.getMessage());
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(ArrayList<LeagueMatch> matches) {
                        callback.onSuccess(matches);
                    }
                });
    }

    public Subscription AcceptLeague(int LeagueId, final ModelCallback<League> callback) {
        return leagueService.AcceptChallange(LeagueId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<League>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(League league) {
                        callback.onSuccess(league);
                    }
                });
    }

    public Subscription RejectLeague(int LeagueId, final ModelCallback<League> callback) {
        return leagueService.RejectChallange(LeagueId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<League>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(League league) {
                        callback.onSuccess(league);
                    }
                });
    }
}
