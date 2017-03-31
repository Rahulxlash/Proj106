package REST.Service;

import REST.Model.LeagueMatch;
import REST.Model.Toss;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by rahul.sharma01 on 3/29/2017.
 */

public interface MatchService {

    @GET("/LeagueMatch/{matchId}")
    public void getMatch(@Path("matchId") int matchId, Callback<LeagueMatch> callback);

    @POST("/LeagueMatch/{MatchId}/RequestToss/{UserId}")
    public void RequestToss(@Path("MatchId") int MatchId, @Path("UserId") int UserId, Callback<LeagueMatch> callback);

    @POST("/LeagueMatch/{MatchId}/DoToss/{Toss}")
    public void DoToss(@Path("MatchId") int MatchId, @Path("Toss") int Toss, Callback<Toss> callback);
}
