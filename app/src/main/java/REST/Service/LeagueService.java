package REST.Service;

import java.util.ArrayList;

import REST.Model.League;
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

    @FormUrlEncoded
    @POST("/league/")
    public void createLeague(@Field("Name") String Name, @Field("Creator") int CreatorId, @Field("Competitor") String CompetitorId, Callback<League> callback);

}
