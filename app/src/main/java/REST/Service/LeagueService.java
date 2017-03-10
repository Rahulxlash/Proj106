package REST.Service;

import java.util.ArrayList;

import REST.Model.League;
import REST.Model.User;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Anuj on 3/10/2017.
 */

public interface LeagueService {
    @GET("/user/{value}/league")
    public void getUserLeague(@Path("value") int userId, Callback<ArrayList<League>> callback);

}
