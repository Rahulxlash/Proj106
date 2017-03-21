package com.cricketta.league.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cricketta.league.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeagueDetails_frag extends Fragment {

    private FragmentTabHost mTabHost;

    public LeagueDetails_frag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_league_details_frag, container, false);
        mTabHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("fragmentb").setIndicator("Summary"),
                LeagueSummary_frag.class, null);
        
        mTabHost.addTab(mTabHost.newTabSpec("fragmentc").setIndicator("All Matches"),
                LeagueMatch_frag.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("fragmentd").setIndicator("Settings"),
                LeagueSetting_frag.class, null);


        return view;
    }

}
