package com.cricketta.league.Utils;

import android.app.Application;

/**
 * Created by rahul.sharma01 on 3/29/2017.
 */

public class CricApplication extends Application {

    private static boolean activityVisible;

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }
}