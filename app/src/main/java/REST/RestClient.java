package REST;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import REST.Service.AuthService;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by rahul.sharma01 on 3/6/2017.
 */

public class RestClient {
    private static final String BASE_URL = "http://192.168.1.109/Cricketta.API/api/";
    private AuthService authService;

    public RestClient() {
        Gson gson = new GsonBuilder().setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .create();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .setConverter(new GsonConverter(gson))
                .build();
        authService = restAdapter.create(AuthService.class);
    }

    public AuthService AuthService() {
        return authService;
    }
}