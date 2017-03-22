package com.cricketta.league.fragment;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cricketta.league.Main_Activity;
import com.cricketta.league.R;

import REST.Model.League;
import REST.RestClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AcceptChallengeFrag_dlg extends DialogFragment {

    String Title;
    int LeagueId;
    private Button btnAccept, btnReject;

    public AcceptChallengeFrag_dlg() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Title = getArguments().getString("name");
        LeagueId = getArguments().getInt("id");
        View rootView = inflater.inflate(R.layout.fragment_accept_challenge_frag_dlg, container, false);
        btnAccept = (Button) rootView.findViewById(R.id.btnAcceptChallange);
        btnReject = (Button) rootView.findViewById(R.id.btnDeclineChallange);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestClient client = new RestClient();
                client.LeagueService().AcceptChallange(LeagueId, new Callback<League>() {
                    @Override
                    public void success(League league, Response response) {
                        FragmentManager manager = getFragmentManager();
                        frag_league_list frag = (frag_league_list) getFragmentManager().findFragmentByTag("Home");
                        frag.getUserLeagues(true);
                        getDialog().dismiss();
                        ((Main_Activity) getActivity()).showToast("League Accepted.");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        ((Main_Activity) getActivity()).showToast("Error occured.");
                    }
                });

            }
        });

        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestClient client = new RestClient();
                client.LeagueService().RejectChallange(LeagueId, new Callback<League>() {
                    @Override
                    public void success(League league, Response response) {
                        FragmentManager manager = getFragmentManager();
                        frag_league_list frag = (frag_league_list) getFragmentManager().findFragmentByTag("Home");
                        frag.getUserLeagues(true);
                        getDialog().dismiss();
                        ((Main_Activity) getActivity()).showToast("League Rejected.");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        ((Main_Activity) getActivity()).showToast("Error occured.");
                    }
                });
            }
        });
        TextView textView = (TextView) rootView.findViewById(R.id.title_accept_league);
        textView.setText(Title);

        return rootView;
    }

}
