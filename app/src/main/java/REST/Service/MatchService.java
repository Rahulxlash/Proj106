package REST.Service;

import REST.ViewModel.LeagueMatch;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by rahul.sharma01 on 3/29/2017.
 */

public interface MatchService {

    @GET("/LeagueMatch/{matchId}/Get")
    Observable<LeagueMatch> getMatch(@Path("matchId") int matchId);

    @POST("/LeagueMatch/{MatchId}/RequestToss/{UserId}")
    Observable<LeagueMatch> RequestToss(@Path("MatchId") int MatchId, @Path("UserId") int UserId);

    @POST("/LeagueMatch/{MatchId}/DoToss/{Toss}")
    Observable<LeagueMatch> DoToss(@Path("MatchId") int MatchId, @Path("Toss") int Toss);
}
