package com.cricketta.league.fragment;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cricketta.league.R;

/**
 * Created by Anuj on 3/29/2017.
 */

public class Request_Toss_dlg extends DialogFragment {

    String Title;

    public Request_Toss_dlg() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_request_toss_dlg, container, false);

        return rootView;
    }
}
