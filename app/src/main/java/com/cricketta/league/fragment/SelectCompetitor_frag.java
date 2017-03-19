package com.cricketta.league.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cricketta.league.R;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectCompetitor_frag extends Fragment {


    public SelectCompetitor_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
            /* handle the result */
                        Log.d("retro", jsonObject.toString());
                    }
                }
        ).executeAsync();

        return inflater.inflate(R.layout.fragment_select_competitor_frag, container, false);
    }

}
