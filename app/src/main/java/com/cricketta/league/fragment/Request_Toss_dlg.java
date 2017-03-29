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

import com.cricketta.league.R;

/**
 * Created by Anuj on 3/29/2017.
 */

public class Request_Toss_dlg extends DialogFragment {

    String Title;
    ObjectAnimator anim;
    Button tossButton;

    public Request_Toss_dlg() {
        // Required empty public constructor
        this.setStyle(STYLE_NO_TITLE, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_request_toss_dlg, container, false);
//        RotateAnimation rotateAnimation = new RotateAnimation(0, 360f,
//                Animation.RELATIVE_TO_SELF, 0.5f,
//                Animation.RELATIVE_TO_SELF, 0.5f);
//
//        rotateAnimation.setInterpolator(new LinearInterpolator());
//        rotateAnimation.setDuration(500);
//        rotateAnimation.setRepeatCount(Animation.INFINITE);

        //rootView.findViewById(R.id.coin).startAnimation(rotateAnimation);
        anim = (ObjectAnimator) AnimatorInflater.loadAnimator(this.getContext(), R.animator.fliping);
        anim.setTarget(rootView.findViewById(R.id.coin));
        anim.setDuration(300);
        anim.setRepeatCount(Animation.INFINITE);


        tossButton = (Button) rootView.findViewById(R.id.TossButton);
        tossButton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              anim.start();
                                          }
                                      }
        );

        return rootView;
    }
}
