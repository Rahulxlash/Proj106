package com.cricketta.league.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cricketta.league.BaseActivity;
import com.cricketta.league.R;

import org.parceler.transfuse.annotations.OnCreate;

import java.util.ArrayList;
import java.util.List;

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

        //leagues = new ArrayList<>();
        getUserLeagues();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void getUserLeagues() {
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
                ((BaseActivity) getActivity()).hideDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                ((BaseActivity) getActivity()).hideDialog();
                Log.d("retro", error.getBody().toString());
            }
        });
    }
}
