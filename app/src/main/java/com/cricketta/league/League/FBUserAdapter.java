package com.cricketta.league.League;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cricketta.league.Main.Main_Activity;
import com.cricketta.league.R;
import com.cricketta.league.fragment.CreateLeague_dlg;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by rahul.sharma01 on 3/19/2017.
 */

public class FBUserAdapter extends RecyclerView.Adapter<FBUserAdapter.FBUserViewHolder> {
    LayoutInflater inflater;
    Context context;
    private JSONArray array;

    public FBUserAdapter(JSONArray data) {
        this.array = data;
    }


    @Override
    public FBUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_facebook_user, parent, false);
        final FBUserViewHolder holder = new FBUserViewHolder(view);
        holder.btn_Challange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String FBId = array.getJSONObject(holder.getAdapterPosition()).getString("id");
                    Bundle bundle = new Bundle();
                    bundle.putString("FBId", FBId);
                    FragmentManager manager = ((Main_Activity) context).getSupportFragmentManager();
                    FragmentTransaction ft = manager.beginTransaction();
                    Fragment prev = manager.findFragmentByTag("dialog");
                    if (prev != null) {
                        ft.remove(prev);
                    }
                    CreateLeague_dlg newFragment = new CreateLeague_dlg();
                    newFragment.setArguments(bundle);
                    newFragment.show(ft, "dialog");
                } catch (JSONException e) {
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(FBUserViewHolder holder, int position) {
        try {
            holder.Name.setText(array.getJSONObject(position).getString("name"));
            String imgUrl = array.getJSONObject(position).getJSONObject("picture").getJSONObject("data").getString("url");
            Picasso.with(context).load(imgUrl).placeholder(R.drawable.prof_ico_1).into(holder.img);
        } catch (JSONException e) {
        }
    }

    @Override
    public int getItemCount() {
        return array.length();
    }

    class FBUserViewHolder extends RecyclerView.ViewHolder {
        TextView Name;
        Button btn_Challange;
        ImageView img;

        public FBUserViewHolder(View itemView) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.img_user);
            Name = (TextView) itemView.findViewById(R.id.txt_FBUserName);
            btn_Challange = (Button) itemView.findViewById(R.id.btn_Challange);
        }

    }
}