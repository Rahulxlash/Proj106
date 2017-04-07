package com.cricketta.league.League;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cricketta.league.BaseFragment;
import com.cricketta.league.LeagueMatch.PlayerList_dlg;
import com.cricketta.league.LeagueMatch.Request_Toss_dlg;
import com.cricketta.league.LeagueMatch.SelectTeam_frag;
import com.cricketta.league.Listener.LeagueListener;
import com.cricketta.league.Main.Main_Activity;
import com.cricketta.league.R;

import java.util.ArrayList;

import REST.Adapter.MatchViewAdapter;
import REST.ViewModel.League;
import REST.ViewModel.LeagueMatch;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class LeagueMatch_frag extends BaseFragment implements LeagueContract.MatchListView {
    @InjectView(R.id.league_match)
    RecyclerView league_match;
    @InjectView(R.id.swipeRefreshLayout_match)
    SwipeRefreshLayout mSwipeRefreshLayout_match;

    MatchViewAdapter adapter;
    private League league;
    private ArrayList<LeagueMatch> matches;
    private LeagueMatchesPresenter presenter;

    public LeagueMatch_frag() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_league_match_frag, container, false);
        ButterKnife.inject(this, rootView);
        presenter = new LeagueMatchesPresenter(this);

        league = (League) getArguments().getSerializable("league");
        league_match.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));
        league_match.addOnItemTouchListener(
                new LeagueListener(getActivity(), league_match, new LeagueListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {
                        LeagueMatch leagueMatch = matches.get(position);
                        presenter.onMatchClick(leagueMatch);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }
                })
        );

        mSwipeRefreshLayout_match.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadLeagueMatches(league.leagueId);
            }
        });
        presenter.loadLeagueMatches(league.leagueId);
        ((Main_Activity) getActivity()).showToast("yo yo");
        return rootView;

    }

    private void onItemsLoadComplete() {
        mSwipeRefreshLayout_match.setRefreshing(false);
    }

    @Override
    public void showMatches(ArrayList<LeagueMatch> matches) {
        this.matches = matches;
        adapter = new MatchViewAdapter(matches);
        league_match.setAdapter(adapter);
        onItemsLoadComplete();
    }

    @Override
    public void showSelectPlayer(LeagueMatch match) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("leagueMatch", match);
        PlayerList_dlg fragment = new PlayerList_dlg();
        fragment.setArguments(bundle);
        ((Main_Activity) getActivity()).showFragment(fragment, "player", true, true);
    }

    @Override
    public void showToss(LeagueMatch match) {
        Bundle bundle = new Bundle();
        bundle.putInt("matchId", match.leagueMatchId);
        FragmentManager fm = getFragmentManager();
        Request_Toss_dlg dialogFragment = new Request_Toss_dlg();
        dialogFragment.setArguments(bundle);
        dialogFragment.show(fm, "Toss Pending");
    }

    @Override
    public void showScoreCard(LeagueMatch match) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("leagueMatch", match);
        SelectTeam_frag fragment = new SelectTeam_frag();
        fragment.setArguments(bundle);
        ((Main_Activity) getActivity()).showFragment(fragment, "player", true, true);
    }
}

