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

import REST.ViewHolder.PlayerViewHolder;
import REST.ViewModel.Player;

/**
 * Created by Anuj on 4/4/2017.
 */

public class PlayerViewAdapter extends RecyclerView.Adapter<PlayerViewHolder> {
    public int Position;
    Context context;
    private List<Player> players;

    public PlayerViewAdapter(List<Player> players) {
        this.players = players;
    }

    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View statusContainer = inflater.inflate(R.layout.select_player, parent, false);
        return new PlayerViewHolder(statusContainer);
    }

    @Override
    public void onBindViewHolder(final PlayerViewHolder holder, int position) {
        Player status = players.get(position);
        ImageView img = (ImageView) holder.itemView.findViewById(R.id.img_user_team_select);
        Picasso.with(context).load("http://api.cricketta.com/images/" + status.photo.trim() + "Logo.png").into(img);
        holder.bind(status);
    }

    @Override
    public int getItemCount() {
        return players.size();
    }
}