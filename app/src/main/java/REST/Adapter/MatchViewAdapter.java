package REST.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cricketta.league.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import REST.Model.LeagueMatch;
import REST.ViewHolder.MatchViewHolder;

/**
 * Created by rahul.sharma01 on 3/23/2017.
 */

public class MatchViewAdapter extends RecyclerView.Adapter<MatchViewHolder> {
    public int Position;
    Context context;
    private List<LeagueMatch> matches;
    public MatchViewAdapter(List<LeagueMatch> matches) {
        this.matches = matches;
    }

    @Override
    public MatchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View statusContainer = inflater.inflate(R.layout.match_summary_card, parent, false);
        return new MatchViewHolder(statusContainer);
    }

    @Override
    public void onBindViewHolder(final MatchViewHolder holder, int position) {
        LeagueMatch status = matches.get(position);
        ImageView img = (ImageView) holder.itemView.findViewById(R.id.team1Logo);
        ImageView img2 = (ImageView) holder.itemView.findViewById(R.id.team2Logo);
        Picasso.with(context).load("http://api.cricketta.com/images/" + status.teamName1.trim() + "Logo.png").into(img);
        Picasso.with(context).load("http://api.cricketta.com/images/" + status.teamName2.trim() + "Logo.png").into(img2);
        holder.bind(status);
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }
}