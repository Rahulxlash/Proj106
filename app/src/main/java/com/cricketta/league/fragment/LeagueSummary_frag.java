package com.cricketta.league.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cricketta.league.Listener.LeagueListener;
import com.cricketta.league.Main_Activity;
import com.cricketta.league.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import REST.Adapter.MatchViewAdapter;
import REST.Model.League;
import REST.Model.LeagueMatch;
import REST.RestClient;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
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
    private ArrayList<LeagueMatch> matches;
    private TextView txtLeagueName, txtCreatorPoint, txtCompetitorPoint, txtMyPoints, txtCompName;
    private ImageView img_user;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public LeagueSummary_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_league_summary_frag, container, false);
        txtLeagueName = (TextView) rootView.findViewById(R.id.txtLeagueName);
        txtCreatorPoint = (TextView) rootView.findViewById(R.id.challenger_point);
        txtCompetitorPoint = (TextView) rootView.findViewById(R.id.competitor_point);
        txtMyPoints = (TextView) rootView.findViewById(R.id.txtMyPoints);
        txtCompName = (TextView) rootView.findViewById(R.id.txtCompName);
        img_user = (ImageView) rootView.findViewById(R.id.img_user);
        league_summmary = (RecyclerView) rootView.findViewById(R.id.league_summmary);
//        league_summmary.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));
//        league_summmary.addOnItemTouchListener(
//                new LeagueListener(getActivity(), league_summmary, new LeagueListener.OnItemClickListener() {
//
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        LeagueMatch leagueMatch =.get(position);
//
//                            Bundle bundle = new Bundle();
//                            bundle.putSerializable("leagueMatch", leagueMatch);
//                            ScoreCard_frag fragment = new ScoreCard_frag();
//                            fragment.setArguments(bundle);
//                            ((Main_Activity) getActivity()).showFragment(fragment, "Score card", true, false);
//
//                    }
//
//                    @Override
//                    public void onLongItemClick(View view, int position) {
//
//                    }
//                })
//        );
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
                txtMyPoints.setText(Integer.toString(league.getPoints()));
                txtCompName.setText(league.getCompetitorName());

                Picasso.with(getActivity()).load("https://graph.facebook.com/" + league.getCompFBId() + "/picture?type=normal").transform(new CropCircleTransformation()).placeholder(R.drawable.prof_ico_1).into(img_user);
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
