package REST.Service;

import java.util.ArrayList;

import REST.Model.League;
import REST.Model.LeagueMatch;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Anuj on 3/10/2017.
 */

public interface LeagueService {
    @GET("/user/{value}/league")
    public void getUserLeague(@Path("value") int userId, Callback<ArrayList<League>> callback);

    @GET("/league/{value}/summary/{userId}")
    public void getLeagueSummary(@Path("value") int leagueId, @Path("userId") int userId, Callback<League> callback);

    @GET("/league/{value}/matches")
    public void getLeagueMatches(@Path("value") int leagueId, Callback<ArrayList<LeagueMatch>> callback);

    @FormUrlEncoded
    @POST("/league/")
    public void createLeague(@Field("Name") String Name, @Field("Creator") int CreatorId, @Field("Competitor") String CompetitorId, Callback<League> callback);

    @POST("/league/{leagueId}/Accept")
    public void AcceptChallange(@Path("leagueId") int LeagueId, Callback<League> callback);

    @POST("/league/{leagueId}/Reject")
    public void RejectChallange(@Path("leagueId") int LeagueId, Callback<League> callback);
}
