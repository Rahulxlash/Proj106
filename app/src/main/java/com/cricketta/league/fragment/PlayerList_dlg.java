package com.cricketta.league.fragment;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cricketta.league.Listener.LeagueListener;
import com.cricketta.league.R;

import java.util.ArrayList;

import REST.Adapter.PlayerViewAdapter;
import REST.RestClient;
import REST.ViewModel.Player;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerList_dlg extends DialogFragment {
    RecyclerView playerList;
    PlayerViewAdapter adapter;
    private int matchId;
    private ArrayList<Player> players;

    public PlayerList_dlg() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_player_list_dlg, container, false);
        playerList = (RecyclerView) rootView.findViewById(R.id.playerList_recyclerView);
        playerList.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));
        playerList.addOnItemTouchListener(
                new LeagueListener(getActivity(), playerList, new LeagueListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
        matchId = getArguments().getInt("matchId");
        getplayerList();
        return rootView;
    }

    public void getplayerList() {
        RestClient client = new RestClient();
//        client.MatchService().getAllPlayer(matchId, new Callback<ArrayList<Player>>() {
//            @Override
//            public void success(ArrayList<Player> player, Response response) {
//                players = player;
//                adapter = new PlayerViewAdapter(players);
//                playerList.setAdapter(adapter);
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//
//            }
//        });
    }
}
