package com.cricketta.league.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cricketta.league.Main_Activity;
import com.cricketta.league.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import REST.Adapter.MatchViewAdapter;
import REST.Model.League;
import REST.Model.LeagueMatch;
import REST.RestClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.android.databinding.library.baseAdapters.BR.league;
import static com.cricketta.league.R.id.img_user;
import static com.cricketta.league.R.id.league_match;
import static com.cricketta.league.R.id.league_recycler;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeagueMatch_frag extends Fragment {
    RecyclerView league_match;
    MatchViewAdapter adapter;
    private int leagueId;
    private SwipeRefreshLayout mSwipeRefreshLayout_match;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public LeagueMatch_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_league_match_frag, container, false);

        league_match = (RecyclerView) rootView.findViewById(R.id.league_summmary);
        league_match.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout_match);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                getLeagueMatches();
            }
        });
        return rootView;
    }

    public void getLeagueMatches() {
        int userId = ((Main_Activity) getActivity()).mintUserId;
        RestClient client = new RestClient();
        client.LeagueService().getLeagueMatches(leagueId, new Callback<ArrayList<LeagueMatch>>() {
            @Override
            public void success(ArrayList<LeagueMatch> leagueMatches, Response response) {
                adapter = new MatchViewAdapter(leagueMatches);
                league_match.setAdapter(adapter);
                onItemsLoadComplete();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }

    private void onItemsLoadComplete() {
        mSwipeRefreshLayout.setRefreshing(false);
    }
}