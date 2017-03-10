package REST.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cricketta.league.R;
import com.cricketta.league.databinding.ItemLeagueListBinding;

import java.util.List;

import REST.Model.League;
import REST.ViewHolder.LeagueViewHolder;

/**
 * Created by Anuj on 3/10/2017.
 */

public class LeagueViewAdapter extends  RecyclerView.Adapter<LeagueViewHolder> {
    private List<League> users;

    public LeagueViewAdapter(List<League> users) {
        this.users = users;
    }

    @Override
    public LeagueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View statusContainer = inflater.inflate(R.layout.item_league_list, parent, false);

        return new LeagueViewHolder(statusContainer);
    }

    @Override
    public void onBindViewHolder(LeagueViewHolder holder, int position) {
        League status = users.get(position);
        holder.bind(status);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}