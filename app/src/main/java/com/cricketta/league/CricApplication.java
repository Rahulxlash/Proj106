package com.cricketta.league;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.cricketta.league.di.Component.AppComponent;
import com.cricketta.league.di.Component.DaggerAppComponent;
import com.cricketta.league.di.Modules.AppModule;
import com.facebook.FacebookSdk;

/**
 * Created by rahul.sharma01 on 3/29/2017.
 */

public class CricApplication extends Application {

    private static AppComponent appComponent;
    private static boolean activityVisible;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}