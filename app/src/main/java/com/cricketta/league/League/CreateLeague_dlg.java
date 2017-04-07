package com.cricketta.league.League;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cricketta.league.BaseDialogFragment;
import com.cricketta.league.R;

import REST.ViewModel.League;
import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateLeague_dlg extends BaseDialogFragment implements LeagueContract.CreateLeagueView {
    @InjectView(R.id.btn_CreateLeague)
    Button btnCreate;
    @InjectView(R.id.League_name)
    EditText txtName;
    @InjectView(R.id.txtWarning)
    TextView txtWarning;

    LeagueContract.CreateLeaguePresenter presenter;

    public CreateLeague_dlg() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setStyle(STYLE_NO_TITLE, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Create League");
        View view = inflater.inflate(R.layout.fragment_dlg__create_league, container, false);
        ButterKnife.inject(this, view);
        presenter = new CreateLeaguePresenter(this);
        final String CompetitorId = getArguments().getString("FBId");
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtName.getText().toString().equals("")) {
                    txtWarning.setVisibility(View.VISIBLE);
                    txtName.requestFocus();
                    return;
                }
                presenter.CreateLeague(txtName.getText().toString(), CompetitorId);
            }
        });

        return view;
    }

    @Override
    public void leagueCreated(League league) {
        FragmentManager manager = getFragmentManager();
        manager.popBackStack();
        frag_league_list frag = (frag_league_list) getFragmentManager().findFragmentByTag("Home");
        frag.presenter.loadLeagues();
        getDialog().dismiss();
    }
}
