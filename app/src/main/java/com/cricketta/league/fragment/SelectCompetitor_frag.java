package com.cricketta.league.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cricketta.league.BaseActivity;
import com.cricketta.league.R;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import REST.Adapter.FBUserAdapter;
import REST.Adapter.LeagueViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectCompetitor_frag extends Fragment {
    private RecyclerView recyclerView;
    private FBUserAdapter adapter;

    public SelectCompetitor_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_competitor_frag, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.fb_user_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));

        ((BaseActivity) getActivity()).showDialog("Loading");
        GraphRequest request = GraphRequest.newMyFriendsRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONArrayCallback() {
                    @Override
                    public void onCompleted(JSONArray array, GraphResponse response) {
                        // Insert your code here
                        adapter = new FBUserAdapter(array);
                        recyclerView.setAdapter(adapter);
                        ((BaseActivity) getActivity()).hideDialog();
                        Log.d("retro", response.getJSONObject().toString());
                    }
                });
        Set<String> fields = new HashSet<String>();
        String[] requiredFields = new String[]{"id", "name", "picture",
                "installed"};
        fields.addAll(Arrays.asList(requiredFields));

        Bundle parameters = request.getParameters();
        parameters.putString("fields", TextUtils.join(",", fields));
        request.setParameters(parameters);
        request.executeAsync();

        return view;
    }

}
