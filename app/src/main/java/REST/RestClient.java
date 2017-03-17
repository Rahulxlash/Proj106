package REST;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import REST.Model.League;
import REST.Service.AuthService;
import REST.Service.LeagueService;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by rahul.sharma01 on 3/6/2017.
 */

public class RestClient {
    private static final String BASE_URL = "http://api.cricketta.com/api/";
    //private static final String BASE_URL = "http://192.168.1.109/cricketta.api/api/";
    private AuthService authService;
    private LeagueService leagueService;

    public RestClient() {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .setConverter(new GsonConverter(gson))
                .build();
        authService = restAdapter.create(AuthService.class);
        leagueService = restAdapter.create(LeagueService.class);
    }
    public AuthService AuthService() {
        return authService;
    }
    public LeagueService LeagueService(){return  leagueService;}
}
