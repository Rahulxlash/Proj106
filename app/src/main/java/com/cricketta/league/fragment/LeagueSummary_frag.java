package com.cricketta.league.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cricketta.league.Main_Activity;
import com.cricketta.league.R;
import com.squareup.picasso.Picasso;

import REST.Adapter.MatchViewAdapter;
import REST.Model.League;
import REST.RestClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeagueSummary_frag extends Fragment {

    RecyclerView league_summmary;
    MatchViewAdapter adapter;
    private int leagueId;
    private TextView txtLeagueName, txtCreatorPoint, txtCompetitorPoint;
    private ImageView img_user;
    private SwipeRefreshLayout mSwipeRefreshLayout;
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
        img_user = (ImageView) rootView.findViewById(R.id.img_user);
        league_summmary = (RecyclerView) rootView.findViewById(R.id.league_summmary);
        league_summmary.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayoutSumary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                getLeagueSummary();
            }
        });
        leagueId = getArguments().getInt("leagueId");
        getLeagueSummary();
        return rootView;

    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void getLeagueSummary() {
        int userId = ((Main_Activity) getActivity()).mintUserId;
        RestClient client = new RestClient();
        client.LeagueService().getLeagueSummary(leagueId, userId, new Callback<League>() {
            @Override
            public void success(League league, Response response) {
                txtLeagueName.setText(league.getName());
                txtCreatorPoint.setText(Integer.toString(league.getCreatorPoints()));
                txtCompetitorPoint.setText(Integer.toString(league.getCompetitorPoints()));
                Picasso.with(getActivity()).load("https://graph.facebook.com/" + league.getCompFBId() + "/picture?type=normal").placeholder(R.drawable.prof_ico_1).into(img_user);
                adapter = new MatchViewAdapter(league.getSummaryMatches());
                league_summmary.setAdapter(adapter);
                onItemsLoadComplete();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
