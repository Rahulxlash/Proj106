package com.cricketta.league.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cricketta.league.BaseActivity;
import com.cricketta.league.Listener.LeagueListener;
import com.cricketta.league.Main_Activity;
import com.cricketta.league.R;

import java.util.ArrayList;

import REST.Adapter.LeagueViewAdapter;
import REST.Model.League;
import REST.RestClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class frag_league_list extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<League> leagues;
    private LeagueViewAdapter adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public frag_league_list() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frag_league_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.league_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addOnItemTouchListener(
                new LeagueListener(getActivity(), recyclerView, new LeagueListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {
                        League league = leagues.get(position);
                        if (league.Accepted() == false && league.getIsMyLeague() == false) {
                            Bundle bundle = new Bundle();
                            bundle.putString("name", league.getName());
                            bundle.putInt("id", league.getLeagueId());

                            FragmentManager fm = getFragmentManager();
                            AcceptChallengeFrag_dlg dialogFragment = new AcceptChallengeFrag_dlg();
                            dialogFragment.setArguments(bundle);
                            dialogFragment.show(fm, "AcceptChallenge");
                        }
                        if (league.Accepted() == true) {
                            LeagueDetails_frag frag = new LeagueDetails_frag();
                            ((Main_Activity) getActivity()).showFragment(frag, "LeagueDetail", true);
                        }
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                getUserLeagues(false);
            }
        });
        //leagues = new ArrayList<>();
        getUserLeagues(true);

        return view;


    }


    @Override
    public void onStart() {
        super.onStart();

    }

    public void getUserLeagues(boolean showProgress) {
        if (showProgress)
            ((BaseActivity) getActivity()).showDialog("");
        SharedPreferences mySharedpreprence = getActivity().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        int User_Id = mySharedpreprence.getInt("USER_ID", 0);
        RestClient restClient = new RestClient();
        restClient.LeagueService().getUserLeague(User_Id, new Callback<ArrayList<League>>() {
            @Override
            public void success(ArrayList<League> league, Response response) {
                leagues = league;
                adapter = new LeagueViewAdapter(leagues);
                recyclerView.setAdapter(adapter);
                onItemsLoadComplete();
                ((BaseActivity) getActivity()).hideDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                onItemsLoadComplete();
                ((BaseActivity) getActivity()).hideDialog();
                Log.d("retro", error.getBody().toString());
            }
        });
    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
    }
}


