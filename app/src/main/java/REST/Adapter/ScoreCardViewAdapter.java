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

import REST.ViewHolder.ScoreCardViewHolder;
import REST.ViewModel.ScoreCard;

/**
 * Created by rahul.sharma01 on 4/10/2017.
 */

public class ScoreCardViewAdapter extends RecyclerView.Adapter<ScoreCardViewHolder> {
    public int Position;
    Context context;
    private List<ScoreCard> cards;

    public ScoreCardViewAdapter(List<ScoreCard> cards) {
        this.cards = cards;
    }

    @Override
    public ScoreCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View statusContainer = inflater.inflate(R.layout.team_player, parent, false);
        return new ScoreCardViewHolder(statusContainer);
    }

    @Override
    public void onBindViewHolder(final ScoreCardViewHolder holder, int position) {
        ScoreCard status = cards.get(position);
        ImageView img = (ImageView) holder.itemView.findViewById(R.id.img_user_team_select);
        Picasso.with(context).load("http://api.cricketta.com/images/" + status.photo.trim() + "Logo.png").into(img);

        holder.bind(status);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }
}