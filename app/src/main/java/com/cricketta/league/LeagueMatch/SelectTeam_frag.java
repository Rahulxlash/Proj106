package com.cricketta.league.LeagueMatch;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cricketta.league.BaseFragment;
import com.cricketta.league.CricApplication;
import com.cricketta.league.R;
import com.cricketta.league.events.PlayerSelectedEvent;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Iterator;

import javax.inject.Inject;

import REST.Adapter.ScoreCardViewAdapter;
import REST.Model.AuthModel;
import REST.ViewModel.LeagueMatch;
import REST.ViewModel.Player;
import REST.ViewModel.ScoreCard;
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
    @InjectView(R.id.my_team)
    RecyclerView recyclerMyTeam;
    @InjectView(R.id.comp_team)
    RecyclerView recyclerCompTeam;

    @Inject
    EventBus eventBus;

    ScoreCardViewAdapter myTeamAdapter;
    ScoreCardViewAdapter compTeamAdapter;
    SelectTeamPresenter presenter;
    private LeagueMatch leagueMatch;

    public SelectTeam_frag() {
        // Required empty public constructor
        CricApplication.getAppComponent().inject(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_select_team_frag, container, false);
        leagueMatch = (LeagueMatch) getArguments().getSerializable("match");
        ButterKnife.inject(this, rootView);
        presenter = new SelectTeamPresenter(this);
        presenter.getAllPlayers(leagueMatch.leagueMatchId);

        txtMatchDate.setText(leagueMatch.getMatchDate());
        txtVenue.setText(leagueMatch.venue);
        recyclerMyTeam.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerCompTeam.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));
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
        bundle.putInt("matchId", leagueMatch.leagueMatchId);
        bundle.putSerializable("leaguematch", leagueMatch);
        bundle.putSerializable("players", presenter.players);
        fragment.setArguments(bundle);
        fragment.show(getFragmentManager(), "selectPlayer");
    }

    public void updatePlayerList(int PlayerId, int userId) {
        Iterator<Player> it = presenter.players.iterator();
        while (it.hasNext()) {
            Player pl = it.next();
            if (pl.playerId == PlayerId) {
                ScoreCard card = new ScoreCard();
                card.playerId = pl.playerId;
                card.name = pl.name;
                card.bat = pl.bat;
                card.bowl = pl.bowl;
                card.keeper = pl.keeper;
                card.captain = pl.captain;
                card.photo = pl.photo;
                card.userId = userId;
                if (userId == AuthModel.UserId)
                presenter.myTeamPlayers.add(card);
                else
                    presenter.compTeamPlayers.add(card);
                it.remove();
                break;
            }
        }
    }

    @Override
    public void showTeam() {
        if (!presenter.myTeamPlayers.isEmpty()) {
            myTeamAdapter = new ScoreCardViewAdapter(presenter.myTeamPlayers);
            recyclerMyTeam.setAdapter(myTeamAdapter);
        }
        if (!presenter.compTeamPlayers.isEmpty()) {
            compTeamAdapter = new ScoreCardViewAdapter(presenter.compTeamPlayers);
            recyclerCompTeam.setAdapter(compTeamAdapter);
        }
    }

    @Subscribe
    public void onMessageEvent(PlayerSelectedEvent event) {
        if (event.matchId == leagueMatch.leagueMatchId) {
            updatePlayerList(event.playerId, event.userId);
            showTeam();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}

