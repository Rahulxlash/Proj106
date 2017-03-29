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
    Button tossButton;
    int matchId;

    public Request_Toss_dlg() {
        // Required empty public constructor
        this.setStyle(STYLE_NO_TITLE, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_request_toss_dlg, container, false);
        matchId = getArguments().getInt("matchId");
        anim = (ObjectAnimator) AnimatorInflater.loadAnimator(this.getContext(), R.animator.fliping);
        anim.setTarget(rootView.findViewById(R.id.coin));
        anim.setDuration(300);
        anim.setRepeatCount(Animation.INFINITE);

        tossButton = (Button) rootView.findViewById(R.id.TossButton);
        tossButton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              RestClient client = new RestClient();
                                              client.MatchService().RequestToss(matchId, ((Main_Activity) getActivity()).mintUserId, new Callback<LeagueMatch>() {
                                                  @Override
                                                  public void success(LeagueMatch leagueMatch, Response response) {
                                                      tossButton.setVisibility(View.INVISIBLE);
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
}
