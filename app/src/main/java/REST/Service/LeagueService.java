package REST.Service;

import java.util.ArrayList;

import REST.ViewModel.League;
import REST.ViewModel.LeagueMatch;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Anuj on 3/10/2017.
 */

public interface LeagueService {
    @GET("user/{value}/league")
    Observable<ArrayList<League>> getUserLeague(@Path("value") int userId);

    @GET("league/{value}/summary/{userId}")
    Observable<League> getLeagueSummary(@Path("value") int leagueId, @Path("userId") int userId);

    @GET("league/{value}/matches/{userId}")
    Observable<ArrayList<LeagueMatch>> getLeagueMatches(@Path("value") int leagueId, @Path("userId") int userId);

    @FormUrlEncoded
    @POST("league/")
    Observable<League> createLeague(@Field("Name") String Name, @Field("Creator") int CreatorId, @Field("Competitor") String CompetitorId);

    @POST("league/{leagueId}/Accept")
    Observable<League> AcceptChallange(@Path("leagueId") int LeagueId);

    @POST("league/{leagueId}/Reject")
    Observable<League> RejectChallange(@Path("leagueId") int LeagueId);
}
