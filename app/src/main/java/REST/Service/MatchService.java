package REST.Service;

import java.util.ArrayList;

import REST.ViewModel.LeagueMatch;
import REST.ViewModel.Player;
import REST.ViewModel.ScoreCard;
import REST.ViewModel.TeamPlayersModel;
import REST.ViewModel.Toss;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by rahul.sharma01 on 3/29/2017.
 */

public interface MatchService {

    @GET("LeagueMatch/{matchId}/Get")
    Observable<LeagueMatch> getMatch(@Path("matchId") int matchId);

    @POST("LeagueMatch/{MatchId}/RequestToss/{UserId}")
    Observable<LeagueMatch> RequestToss(@Path("MatchId") int MatchId, @Path("UserId") int UserId);

    @POST("LeagueMatch/{MatchId}/DoToss/{Toss}")
    Observable<Toss> DoToss(@Path("MatchId") int MatchId, @Path("Toss") int Toss);

    @GET("LeagueMatch/{MatchId}/getAllPlayers")
    Observable<TeamPlayersModel> getAllPlayers(@Path("MatchId") int MatchId);

    @POST("LeagueMatch/{MatchId}/addTeamPlayer")
    @FormUrlEncoded
    Observable<ScoreCard> addPlayer(@Path("MatchId") int MatchId, @Field("UserId") int UserId, @Field("PlayerId") int PlayerId);

    @GET("LeagueMatch/{MatchId}/getScoreCard/{UserId}")
    Observable<ArrayList<ScoreCard>> getScoreCard(@Path("MatchId") int MatchId, @Path("UserId") int UserId);
}
