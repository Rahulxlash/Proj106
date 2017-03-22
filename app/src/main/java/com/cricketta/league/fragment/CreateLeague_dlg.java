package com.cricketta.league.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cricketta.league.R;

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
        //setStyle(DialogFragment.STYLE_NORMAL,android.R.style.Theme);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDialog().setTitle("Create League");
        //getDialog().getWindow().setLayout(400, 400);
        View view = inflater.inflate(R.layout.fragment_dlg__create_league, container, false);
        txtName = (EditText) view.findViewById(R.id.League_name);
        txtWarning = (TextView) view.findViewById(R.id.txtWarning);
        btnCreate = (Button) view.findViewById(R.id.btn_CreateLeague);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtName.getText().toString().equals("")) {
                    txtWarning.setVisibility(View.VISIBLE);
                    txtName.requestFocus();
                    return;
                }


            }
        });

        return view;
    }

}
