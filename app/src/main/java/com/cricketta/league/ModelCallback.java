package com.cricketta.league;

/**
 * Created by rahul.sharma01 on 4/5/2017.
 */

public interface ModelCallback<T> {
    void onSuccess(T response);

    void onError(String networkError);
}
