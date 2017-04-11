package com.cricketta.league.LeagueMatch;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import com.cricketta.league.BaseActivity;
import com.cricketta.league.BaseDialogFragment;
import com.cricketta.league.R;
import com.cricketta.league.events.TossEvent;

import org.greenrobot.eventbus.EventBus;

import REST.Model.AuthModel;
import REST.ViewModel.LeagueMatch;
import REST.ViewModel.Toss;
import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by Anuj on 3/29/2017.
 */

public class Request_Toss_dlg extends BaseDialogFragment implements MatchContract.TossView {

    String Title;
    ObjectAnimator anim;
    @InjectView(R.id.TossButton)
    Button btnToss;
    @InjectView(R.id.Headbutton)
    Button btnHead;
    @InjectView(R.id.Tailbutton)
    Button btnTail;
    @InjectView(R.id.txtResult)
    TextView txtResult;

    LeagueMatch match;
    MatchTossPresenter presenter;
    int tossOption;

    public Request_Toss_dlg() {
        // Required empty public constructor
        this.setStyle(STYLE_NO_TITLE, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_request_toss_dlg, container, false);
        match = (LeagueMatch) getArguments().getSerializable("match");
        ButterKnife.inject(this, rootView);
        presenter = new MatchTossPresenter(this);

        anim = (ObjectAnimator) AnimatorInflater.loadAnimator(this.getContext(), R.animator.fliping);
        anim.setTarget(rootView.findViewById(R.id.coin));
        anim.setDuration(300);
        anim.setRepeatCount(Animation.INFINITE);

        btnHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doToss(1);
            }
        });
        btnTail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doToss(2);
            }
        });
        setUI();

        btnToss.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           presenter.RequestToss(match.leagueMatchId);
                                       }
                                   }
        );

        return rootView;
    }

    private void setUI() {

        if (match.toss == 0) {
            btnToss.setVisibility(View.VISIBLE);
            btnHead.setVisibility(View.GONE);
            btnTail.setVisibility(View.GONE);
        } else if (match.toss == -1) {
            anim.start();
            btnToss.setVisibility(View.GONE);
            if (match.tossRequestedBy != AuthModel.UserId) {
                btnHead.setVisibility(View.VISIBLE);
                btnTail.setVisibility(View.VISIBLE);
            }
        }
    }

    private void doToss(final int tossOption) {
        btnHead.setEnabled(false);
        btnTail.setEnabled(false);
        this.tossOption = tossOption;
        presenter.doToss(match.leagueMatchId, tossOption);
    }

    @Override
    public void tossSubmitted() {
        btnToss.setVisibility(View.INVISIBLE);
        ((BaseActivity) getActivity()).showToast("Toss request sent.");
        getDialog().dismiss();
    }

    @Override
    public void tossDone(Toss toss) {
        if (tossOption == toss.result)
            ((BaseActivity) getActivity()).showToast("Congrats!! You won the toss now select the team.");
        else
            ((BaseActivity) getActivity()).showToast("Ohh!! You lost the toss, now select the team.");

        txtResult.setText(toss.value);
        txtResult.setVisibility(View.VISIBLE);
        anim.end();

        EventBus.getDefault().post(new TossEvent(match.leagueMatchId));
    }
}
