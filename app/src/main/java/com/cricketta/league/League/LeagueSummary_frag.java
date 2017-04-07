package com.cricketta.league.League;


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

import com.cricketta.league.BaseFragment;
import com.cricketta.league.R;
import com.squareup.picasso.Picasso;

import REST.Adapter.MatchViewAdapter;
import REST.ViewModel.League;
import butterknife.ButterKnife;
import butterknife.InjectView;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;


/**
 * A simple {@link Fragment} subclass.
 */
public class LeagueSummary_frag extends BaseFragment implements LeagueContract.SummaryView {
    @InjectView(R.id.league_summmary)
    RecyclerView league_summmary;
    @InjectView(R.id.txtLeagueName)
    TextView txtLeagueName;
    @InjectView(R.id.challenger_point)
    TextView txtCreatorPoint;
    @InjectView(R.id.competitor_point)
    TextView txtCompetitorPoint;
    @InjectView(R.id.txtMyPoints)
    TextView txtMyPoints;
    @InjectView(R.id.txtCompName)
    TextView txtCompName;
    @InjectView(R.id.img_user)
    ImageView img_user;
    @InjectView(R.id.swipeRefreshLayoutSumary)
    SwipeRefreshLayout mSwipeRefreshLayout;

    MatchViewAdapter adapter;
    LeagueSummaryPresenter presenter;
    League league;

    public LeagueSummary_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_league_summary_frag, container, false);
        ButterKnife.inject(this, rootView);
        league = (League) getArguments().getSerializable("league");
        presenter = new LeagueSummaryPresenter(this);

        league_summmary.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadSummaryMatches(league.leagueId);
            }
        });
        presenter.loadSummaryMatches(league.leagueId);
        return rootView;

    }

    void onItemsLoadComplete() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showSummary(League league) {
        this.league = league;
        txtLeagueName.setText(league.getName());
        txtCreatorPoint.setText(Integer.toString(league.getCreatorPoints()));
        txtCompetitorPoint.setText(Integer.toString(league.getCompetitorPoints()));
        txtMyPoints.setText(Integer.toString(league.getPoints()));
        txtCompName.setText(league.getCompetitorName());

        Picasso.with(getActivity()).load("https://graph.facebook.com/" + league.getCompFBId() + "/picture?type=normal").transform(new CropCircleTransformation()).placeholder(R.drawable.prof_ico_1).into(img_user);
        adapter = new MatchViewAdapter(league.getSummaryMatches());
        league_summmary.setAdapter(adapter);
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
