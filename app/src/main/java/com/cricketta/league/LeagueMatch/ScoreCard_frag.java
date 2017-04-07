package com.cricketta.league.LeagueMatch;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cricketta.league.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScoreCard_frag extends Fragment {


    public ScoreCard_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_score_card_frag, container, false);
    }

}
