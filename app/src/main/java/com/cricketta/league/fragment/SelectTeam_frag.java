package com.cricketta.league.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cricketta.league.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

import REST.Model.LeagueMatch;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectTeam_frag extends Fragment {
    private LeagueMatch leagueMatch;

    public SelectTeam_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_select_team_frag, container, false);
        leagueMatch = (LeagueMatch) getArguments().getSerializable("leagueMatch");


        ImageView img3 = (ImageView) rootView.findViewById(R.id.imageView_team1);
        ImageView img4 = (ImageView) rootView.findViewById(R.id.imageView_team2);
        Picasso.with(getContext()).load("http://api.cricketta.com/images/" + leagueMatch.teamName1.trim() + "Logo.png").into(img3);
        Picasso.with(getContext()).load("http://api.cricketta.com/images/" + leagueMatch.teamName1.trim() + "Logo.png").into(img4);
        return rootView;

    }


}

