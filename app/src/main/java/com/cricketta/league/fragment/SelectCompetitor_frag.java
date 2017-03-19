package com.cricketta.league.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cricketta.league.R;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectCompetitor_frag extends Fragment {


    public SelectCompetitor_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        GraphRequest.newMeRequest(
//                AccessToken.getCurrentAccessToken(),
//                new GraphRequest.GraphJSONObjectCallback() {
//                    public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
//                        try {
//                            JSONArray jsonArrayFriends = jsonObject.getJSONObject("friendlist").getJSONArray("data");
//                            JSONObject friendlistObject = jsonArrayFriends.getJSONObject(0);
//                            String friendListID = friendlistObject.getString("id");
//                            myNewGraphReq(friendListID);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//        ).executeAsync();

//        new GraphRequest(
//                AccessToken.getCurrentAccessToken(),
//                "/" + AccessToken.getCurrentAccessToken().getUserId() + "/invitable_friends",
//                null,
//                HttpMethod.GET,
//                new GraphRequest.Callback() {
//                    public void onCompleted(GraphResponse response) {
//            /* handle the result */
//                        Log.d("retro",response.getJSONObject().toString());
//                    }
//                }
//        ).executeAsync();
        GraphRequest request = GraphRequest.newMyFriendsRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONArrayCallback() {
                    @Override
                    public void onCompleted(JSONArray array, GraphResponse response) {
                        // Insert your code here
                        Log.d("retro", response.getJSONObject().toString());
                    }
                });

        request.executeAsync();

        return inflater.inflate(R.layout.fragment_select_competitor_frag, container, false);
    }

    private void myNewGraphReq(String friendlistId) {
        final String graphPath = "/" + friendlistId + "/members/";
        //AccesToken token = ;
        GraphRequest request = new GraphRequest(AccessToken.getCurrentAccessToken(), graphPath, null, HttpMethod.GET, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {
                JSONObject object = graphResponse.getJSONObject();
                try {
                    JSONArray arrayOfUsersInFriendList = object.getJSONArray("data");
                /* Do something with the user list */
                /* ex: get first user in list, "name" */
                    JSONObject user = arrayOfUsersInFriendList.getJSONObject(0);
                    String usersName = user.getString("name");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle param = new Bundle();
        param.putString("fields", "name");
        request.setParameters(param);
        request.executeAsync();
    }
}
