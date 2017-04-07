package com.cricketta.league.League;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cricketta.league.BaseFragment;
import com.cricketta.league.R;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONArray;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectCompetitor_frag extends BaseFragment {
    private RecyclerView recyclerView;
    private FBUserAdapter adapter;

    public SelectCompetitor_frag() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_competitor_frag, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.fb_user_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));
        showProgress("Loading Friends....");
        GraphRequest request = GraphRequest.newMyFriendsRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONArrayCallback() {
                    @Override
                    public void onCompleted(JSONArray array, GraphResponse response) {
                        // Insert your code here
                        adapter = new FBUserAdapter(array);
                        recyclerView.setAdapter(adapter);
                        hideProgress();
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
