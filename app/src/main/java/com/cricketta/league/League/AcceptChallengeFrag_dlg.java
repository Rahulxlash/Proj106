package com.cricketta.league.League;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cricketta.league.BaseDialogFragment;
import com.cricketta.league.R;
import com.cricketta.league.events.LeagueChangeEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AcceptChallengeFrag_dlg extends BaseDialogFragment implements LeagueContract.ChallangeView {

    String Title;
    int LeagueId;
    @InjectView(R.id.btnAcceptChallange)
    Button btnAccept;
    @InjectView(R.id.btnDeclineChallange)
    Button btnReject;
    LeagueChallangePresenter presenter;

    public AcceptChallengeFrag_dlg() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Title = getArguments().getString("name");
        LeagueId = getArguments().getInt("id");
        View rootView = inflater.inflate(R.layout.fragment_accept_challenge_frag_dlg, container, false);
        ButterKnife.inject(this, rootView);
        presenter = new LeagueChallangePresenter(this);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.AccpetChallange(LeagueId);
            }
        });

        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.RejectChallange(LeagueId);
            }
        });
        TextView textView = (TextView) rootView.findViewById(R.id.title_accept_league);
        textView.setText(Title);

        return rootView;
    }

    @Override
    public void hideView() {
        getDialog().dismiss();
        EventBus.getDefault().post(new LeagueChangeEvent());
    }

    @Override
    public void onError(String message) {
        super.onError(message);
        showToast("An Error occured..");
    }
}
