package com.cricketta.league.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cricketta.league.R;

import REST.Model.League;
import REST.RestClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeagueSummary_frag extends Fragment {

    private int leagueId;
    private TextView txtLeagueName, txtCreatorPoint, txtCompetitorPoint;


    public LeagueSummary_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_league_summary_frag, container, false);
        txtLeagueName = (TextView) rootView.findViewById(R.id.txtLeagueName);
        txtCreatorPoint = (TextView) rootView.findViewById(R.id.challenger_point);
        txtCompetitorPoint = (TextView) rootView.findViewById(R.id.competitor_point);
        leagueId = getArguments().getInt("leagueId");
        getLeagueSummary();
        return rootView;

    }


    public void getLeagueSummary() {
        RestClient client = new RestClient();
        client.LeagueService().getLeagueSummary(leagueId, new Callback<League>() {
            @Override
            public void success(League league, Response response) {
                txtLeagueName.setText(league.getName());

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
