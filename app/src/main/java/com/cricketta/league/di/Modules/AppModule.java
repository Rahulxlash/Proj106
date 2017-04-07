package com.cricketta.league.di.Modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import REST.Model.AuthModel;
import REST.Model.LeagueModel;
import REST.Model.MatchModel;
import REST.Service.AuthService;
import REST.Service.LeagueService;
import REST.Service.MatchService;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by rahul.sharma01 on 4/2/2017.
 */
@Module
public class AppModule {
    private static final String BASE_URL = "http://api.cricketta.com/api/";
    private Context context;
    private File cachefile;
    //private static final String BASE_URL = "http://192.168.1.109/cricketta.api/api/";

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public Context providesContext() {
        return context;
    }

    @Provides
    @Singleton
    Retrofit providesRestClient() {
        //return new RestClient(BASE_URL, RestAdapter.LogLevel.FULL);
        //return new RestClient();
        Cache cache = null;
        cache = new Cache(new File("http"), 10 * 1024 * 1024);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        // Customize the request
                        Request request = original.newBuilder()
                                .header("Content-Type", "application/json")
                                .removeHeader("Pragma")
                                .header("Cache-Control", String.format("max-age=%d", 432000))
                                .build();

                        okhttp3.Response response = chain.proceed(request);
                        response.cacheResponse();
                        // Customize or return the response
                        return response;
                    }
                })
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .cache(cache)
                .build();

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return restAdapter;
    }

    @Provides
    @Singleton
    AuthService providesAuthService(Retrofit retrofit) {
        return retrofit.create(AuthService.class);
    }

    @Provides
    @Singleton
    LeagueService providesLeagueService(Retrofit retrofit) {
        return retrofit.create(LeagueService.class);
    }

    @Provides
    @Singleton
    MatchService providesMatchService(Retrofit retrofit) {
        return retrofit.create(MatchService.class);
    }

    @Provides
    @Singleton
    EventBus providesEventBus() {
        return new EventBus();
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPrefrences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @Inject
    Picasso providesPicasso() {
        return Picasso.with(context);
    }

    @Provides
    @Singleton
    AuthModel providesAuthModel() {
        return new AuthModel();
    }

    @Provides
    @Singleton
    LeagueModel providesLeagueModel() {
        return new LeagueModel();
    }

    @Provides
    @Inject
    MatchModel providesMatchModel() {
        return new MatchModel();
    }
}
