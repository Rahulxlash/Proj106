package com.cricketta.league.LeagueMatch;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cricketta.league.Listener.LeagueListener;
import com.cricketta.league.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

import REST.Adapter.PlayerViewAdapter;
import REST.ViewModel.LeagueMatch;
import REST.ViewModel.Player;
import butterknife.ButterKnife;
import butterknife.InjectView;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerList_dlg extends DialogFragment {
    RecyclerView playerList;
    LeagueMatch leagueMatch;
    PlayerViewAdapter adapter;
    @InjectView(R.id.imagebutton_me)
    ImageView imgME;
    @InjectView(R.id.imagebutton_competitor)
    ImageView img_competitor;
    private int matchId;
    private ArrayList<Player> players;

    public PlayerList_dlg() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_player_list_dlg, container, false);
        ButterKnife.inject(this, rootView);
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
        leagueMatch = (LeagueMatch) getArguments().getSerializable("leaguematch");
        Picasso.with(getActivity()).load("http://api.cricketta.com/images/" + leagueMatch.teamName1.trim() + "Logo.png").transform(new CropCircleTransformation()).into(imgME);
        Picasso.with(getActivity()).load("http://api.cricketta.com/images/" + leagueMatch.teamName2.trim() + "Logo.png").transform(new CropCircleTransformation()).into(img_competitor);
        matchId = getArguments().getInt("matchId");

        ArrayList<Player> players = (ArrayList<Player>) getArguments().getSerializable("players");
        setplayerList(players);
        return rootView;
    }

    public void setplayerList(ArrayList<Player> players) {

        adapter = new PlayerViewAdapter(players);
        playerList.setAdapter(adapter);
    }
}
