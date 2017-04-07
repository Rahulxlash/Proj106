package com.cricketta.league.fragment;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cricketta.league.Main_Activity;
import com.cricketta.league.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import REST.Model.LeagueMatch;
import REST.Service.AuthService;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectTeam_frag extends DialogFragment {
    ImageButton btnAdd;
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
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        TextView txtMatchDate = (TextView) rootView.findViewById(R.id.Date_Of_Match);
        TextView txtVenue = (TextView) rootView.findViewById(R.id.venu_of_match);
        TextView txtCompetitor = (TextView) rootView.findViewById(R.id.text_competitor);
//        txtCompetitor.setText(leagueMatch.);
        btnAdd = (ImageButton) rootView.findViewById(R.id.AddButton);
        txtMatchDate.setText(format.format(leagueMatch.matchDate));
        txtVenue.setText(leagueMatch.venue.toString());
        ImageView img3 = (ImageView) rootView.findViewById(R.id.imageView_team1);
        ImageView img4 = (ImageView) rootView.findViewById(R.id.imageView_team2);
        Picasso.with(getContext()).load("http://api.cricketta.com/images/" + leagueMatch.teamName1.trim() + "Logo.png").into(img3);
        Picasso.with(getContext()).load("http://api.cricketta.com/images/" + leagueMatch.teamName2.trim() + "Logo.png").into(img4);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("matchId", leagueMatch.leagueMatchId);
                FragmentManager fm = getFragmentManager();
                PlayerList_dlg dialogFragment = new PlayerList_dlg();
                dialogFragment.setArguments(bundle);
                dialogFragment.show(fm, "play list");

            }
        });
        return rootView;

    }


}

