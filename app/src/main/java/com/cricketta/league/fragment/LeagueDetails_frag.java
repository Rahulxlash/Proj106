package com.cricketta.league.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.os.CancellationSignal;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cricketta.league.R;

import java.util.ArrayList;
import java.util.List;

import REST.Model.League;

import static com.android.databinding.library.baseAdapters.BR.league;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeagueDetails_frag extends Fragment {

    //private FragmentTabHost mTabHost;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<League> leagues;
    public LeagueDetails_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_league_details_frag, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.league_summary);
        tabLayout.getTabAt(1).setIcon(R.drawable.league_matches);
        tabLayout.getTabAt(2).setIcon(R.drawable.league_setting);
        return view;


    }

    private void setupViewPager(ViewPager viewPager) {
        Bundle bundle = new Bundle();
        bundle.putInt("leagueId", getArguments().getInt("leagueId"));

        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());

        LeagueSummary_frag fragSum = new LeagueSummary_frag();
        fragSum.setArguments(bundle);
        LeagueMatch_frag fragmat = new LeagueMatch_frag();
        fragmat.setArguments(bundle);
        LeagueSetting_frag fragset = new LeagueSetting_frag();
        fragset.setArguments(bundle);

        adapter.addFragment(fragSum, "");
        adapter.addFragment(fragmat, "");
        adapter.addFragment(fragset, "");

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
