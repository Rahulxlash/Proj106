package com.cricketta.league.League;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cricketta.league.BaseFragment;
import com.cricketta.league.Listener.LeagueListener;
import com.cricketta.league.Main.Main_Activity;
import com.cricketta.league.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import REST.Adapter.LeagueViewAdapter;
import REST.ViewModel.League;
import butterknife.ButterKnife;
import butterknife.InjectView;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

import static com.cricketta.league.BR.league;
import static com.cricketta.league.R.id.img_user;

/**
 * A simple {@link Fragment} subclass.
 */
public class frag_league_list extends BaseFragment implements LeagueContract.ListView {
    @InjectView(R.id.league_recycler)
    RecyclerView recyclerView;
    @InjectView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    LeagueListPresenter presenter;
    private ArrayList<League> leagues;
    private LeagueViewAdapter adapter;


    public frag_league_list() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_frag_league_list, container, false);
        ButterKnife.inject(this, view);
        presenter = new LeagueListPresenter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addOnItemTouchListener(
                new LeagueListener(getActivity(), recyclerView, new LeagueListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {
                        League league = presenter.Leagues.get(position);
                        presenter.onLeagueClick(league);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadLeagues();
            }
        });
        presenter.loadLeagues();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    void onItemsLoadComplete() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showList(ArrayList<League> leagues) {

        adapter = new LeagueViewAdapter(leagues);
        recyclerView.setAdapter(adapter);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLeagueDetails(League league) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("league", league);
        LeagueDetails_frag frag = new LeagueDetails_frag();
        frag.setArguments(bundle);
        ((Main_Activity) getActivity()).showFragment(frag, "LeagueDetail", true, false);
    }

    public void showAcceptChallange(League league) {
        Bundle bundle = new Bundle();
        bundle.putString("name", league.getName());
        bundle.putInt("id", league.getLeagueId());
        FragmentManager fm = getFragmentManager();
        AcceptChallengeFrag_dlg dialogFragment = new AcceptChallengeFrag_dlg();
        dialogFragment.setArguments(bundle);
        dialogFragment.show(fm, "AcceptChallenge");
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }
}


