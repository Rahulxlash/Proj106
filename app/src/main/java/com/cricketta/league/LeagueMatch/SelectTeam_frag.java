package com.cricketta.league.LeagueMatch;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cricketta.league.BaseFragment;
import com.cricketta.league.R;
import com.squareup.picasso.Picasso;

import REST.ViewModel.LeagueMatch;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectTeam_frag extends BaseFragment implements MatchContract.TeamSelectView {
    @InjectView(R.id.Date_Of_Match)
    TextView txtMatchDate;
    @InjectView(R.id.venu_of_match)
    TextView txtVenue;
    @InjectView(R.id.imageView_team1)
    ImageView img3;
    @InjectView(R.id.imageView_team2)
    ImageView img4;
    @InjectView(R.id.text_competitor)
    TextView txt_CompetitorName;
    @InjectView(R.id.btnAdd)
    ImageButton btnAdd;
    SelectTeamPresenter presenter;
    private LeagueMatch leagueMatch;

    public SelectTeam_frag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_select_team_frag, container, false);
        leagueMatch = (LeagueMatch) getArguments().getSerializable("match");
        ButterKnife.inject(this, rootView);
        presenter = new SelectTeamPresenter(this);
        presenter.getAllPlayers(leagueMatch.matchId);

        txtMatchDate.setText(leagueMatch.getMatchDate());
        txtVenue.setText(leagueMatch.venue.toString());
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onAddClick();
            }
        });
        Picasso.with(getContext()).load("http://api.cricketta.com/images/" + leagueMatch.teamName1.trim() + "Logo.png").into(img3);
        Picasso.with(getContext()).load("http://api.cricketta.com/images/" + leagueMatch.teamName2.trim() + "Logo.png").into(img4);
        return rootView;
    }

    @Override
    public void showPlayerList() {
        PlayerList_dlg fragment = new PlayerList_dlg();
        Bundle bundle = new Bundle();
        bundle.putInt("matchid", leagueMatch.matchId);
        bundle.putSerializable("leaguematch", leagueMatch);
        bundle.putSerializable("players", presenter.players);
        fragment.setArguments(bundle);
        fragment.show(getFragmentManager(), "selectPlayer");
    }

    @Override
    public void showTeam() {

    }
}

