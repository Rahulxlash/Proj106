package REST.Model;

import com.cricketta.league.CricApplication;
import com.cricketta.league.ModelCallback;

import javax.inject.Inject;

import REST.Service.MatchService;
import REST.ViewModel.LeagueMatch;
import REST.ViewModel.Toss;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by rahul.sharma01 on 4/7/2017.
 */

public class MatchModel {
    @Inject
    AuthModel authModel;
    @Inject
    MatchService matchService;

    public MatchModel() {
        CricApplication.getAppComponent().inject(this);
    }

    public Subscription requestToss(int matchId, final ModelCallback<LeagueMatch> callback) {
        return matchService.RequestToss(matchId, AuthModel.UserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LeagueMatch>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(LeagueMatch leagueMatch) {
                        callback.onSuccess(leagueMatch);
                    }
                });
    }

    public Subscription doToss(int matchId, int tossOption, final ModelCallback<Toss> callback) {
        return matchService.DoToss(matchId, tossOption)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Toss>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(Toss leagueMatch) {
                        callback.onSuccess(leagueMatch);
                    }
                });
    }
}
