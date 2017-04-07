package com.cricketta.league.LeagueMatch;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cricketta.league.Main.Main_Activity;
import com.cricketta.league.R;
import com.squareup.picasso.Picasso;

import REST.ViewModel.LeagueMatch;

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
        TextView txtMatchDate = (TextView) rootView.findViewById(R.id.Date_Of_Match);
        TextView txtVenue = (TextView) rootView.findViewById(R.id.venu_of_match);
        txtMatchDate.setText(leagueMatch.matchDate.toString());
        txtVenue.setText(leagueMatch.venue.toString());
        ImageView img3 = (ImageView) rootView.findViewById(R.id.imageView_team1);
        ImageView img4 = (ImageView) rootView.findViewById(R.id.imageView_team2);
        Picasso.with(getContext()).load("http://api.cricketta.com/images/" + leagueMatch.teamName1.trim() + "Logo.png").into(img3);
        Picasso.with(getContext()).load("http://api.cricketta.com/images/" + leagueMatch.teamName2.trim() + "Logo.png").into(img4);
        ((Main_Activity) getActivity()).showToast("abc");
        return rootView;

    }


}

