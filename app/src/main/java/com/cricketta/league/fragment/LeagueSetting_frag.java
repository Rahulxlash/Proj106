package com.cricketta.league.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cricketta.league.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeagueSetting_frag extends Fragment {


    public LeagueSetting_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_league_setting_frag, container, false);
        return rootView;
    }

}
