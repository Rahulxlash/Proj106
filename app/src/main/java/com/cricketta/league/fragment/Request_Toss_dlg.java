package com.cricketta.league.fragment;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;

import com.cricketta.league.Main_Activity;
import com.cricketta.league.R;

import REST.Model.LeagueMatch;
import REST.RestClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Anuj on 3/29/2017.
 */

public class Request_Toss_dlg extends DialogFragment {

    String Title;
    ObjectAnimator anim;
    Button btnToss, btnHead, btnTail;
    int matchId;
    LeagueMatch match;

    public Request_Toss_dlg() {
        // Required empty public constructor
        this.setStyle(STYLE_NO_TITLE, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_request_toss_dlg, container, false);
        matchId = getArguments().getInt("matchId");
        if (matchId == 0)
            matchId = Integer.parseInt(getArguments().getString("matchId"));

        anim = (ObjectAnimator) AnimatorInflater.loadAnimator(this.getContext(), R.animator.fliping);
        anim.setTarget(rootView.findViewById(R.id.coin));
        anim.setDuration(300);
        anim.setRepeatCount(Animation.INFINITE);

        btnToss = (Button) rootView.findViewById(R.id.TossButton);
        btnHead = (Button) rootView.findViewById(R.id.Headbutton);
        btnTail = (Button) rootView.findViewById(R.id.Tailbutton);
        getMatch();

        btnToss.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           RestClient client = new RestClient();
                                           client.MatchService().RequestToss(matchId, ((Main_Activity) getActivity()).mintUserId, new Callback<LeagueMatch>() {
                                               @Override
                                               public void success(LeagueMatch leagueMatch, Response response) {
                                                   btnToss.setVisibility(View.INVISIBLE);
                                                   anim.start();
                                               }

                                               @Override
                                               public void failure(RetrofitError error) {

                                               }
                                           });
                                       }
                                   }
        );

        return rootView;
    }

    private void getMatch() {
        RestClient client = new RestClient();
        client.MatchService().getMatch(matchId, new Callback<LeagueMatch>() {
            @Override
            public void success(LeagueMatch leagueMatch, Response response) {
                match = leagueMatch;
                if (match.toss == 0) {
                    btnToss.setVisibility(View.VISIBLE);
                    btnHead.setVisibility(View.GONE);
                    btnTail.setVisibility(View.GONE);
                } else if (match.toss == -1) {
                    anim.start();
                    btnToss.setVisibility(View.GONE);
                    if (match.tossRequestedBy != ((Main_Activity) getActivity()).mintUserId) {
                        btnHead.setVisibility(View.VISIBLE);
                        btnTail.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
