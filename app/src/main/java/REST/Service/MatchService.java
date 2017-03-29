package REST.Service;

import REST.Model.LeagueMatch;
import retrofit.Callback;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by rahul.sharma01 on 3/29/2017.
 */

public interface MatchService {

    @POST("/LeagueMatch/{MatchId}/RequestToss/{UserId}")
    public void RequestToss(@Path("MatchId") int MatchId, @Path("UserId") int UserId, Callback<LeagueMatch> callback);
}
