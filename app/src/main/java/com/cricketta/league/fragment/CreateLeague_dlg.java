package com.cricketta.league.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
public class CreateLeague_dlg extends DialogFragment {

    Button btnCreate;
    EditText txtName;
    TextView txtWarning;

    public CreateLeague_dlg() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDialog().setTitle("Create League");
        View view = inflater.inflate(R.layout.fragment_dlg__create_league, container, false);
        txtName = (EditText) view.findViewById(R.id.League_name);
        txtWarning = (TextView) view.findViewById(R.id.txtWarning);
        btnCreate = (Button) view.findViewById(R.id.btn_CreateLeague);
        final String CompetitorId = getArguments().getString("FBId");
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Main_Activity) getActivity()).showDialog("Creating League");
                if (txtName.getText().toString().equals("")) {
                    txtWarning.setVisibility(View.VISIBLE);
                    txtName.requestFocus();
                    ((Main_Activity) getActivity()).hideDialog();
                    return;
                }
                int creator = ((Main_Activity) getActivity()).mintUserId;

                RestClient client = new RestClient();
                client.LeagueService().createLeague(txtName.getText().toString(), creator, CompetitorId, new Callback<League>() {
                    @Override
                    public void success(League league, Response response) {

                        ((Main_Activity) getActivity()).hideDialog();

                        FragmentManager manager = getFragmentManager();
                        manager.popBackStack();
                        frag_league_list frag = (frag_league_list) getFragmentManager().findFragmentByTag("Home");
                        frag.getUserLeagues();
                        getDialog().dismiss();
                        ((Main_Activity) getActivity()).showToast("League Created");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        ((Main_Activity) getActivity()).hideDialog();
                        ((Main_Activity) getActivity()).showToast("League creation failed");
                    }
                });


            }
        });

        return view;
    }

}
