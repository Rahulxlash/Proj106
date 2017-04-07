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

import REST.ViewHolder.LeagueViewHolder;
import REST.ViewModel.League;
import butterknife.InjectView;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

import static com.cricketta.league.BR.league;

/**
 * Created by Anuj on 3/10/2017.
 */

public class LeagueViewAdapter extends RecyclerView.Adapter<LeagueViewHolder> {
    public int Position;
    Context context;
    ImageView img_user;
    private List<League> users;
    public LeagueViewAdapter(List<League> users) {
        this.users = users;
    }

    @Override
    public LeagueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View statusContainer = inflater.inflate(R.layout.item_league_list, parent, false);

        return new LeagueViewHolder(statusContainer);
    }

    @Override
    public void onBindViewHolder(final LeagueViewHolder holder, int position) {
        League status = users.get(position);

        img_user = (ImageView) holder.itemView.findViewById(R.id.competitor_imageview);
        Picasso.with(context).load("https://graph.facebook.com/" + status.getCompFBId().trim() + "/picture?type=normal").transform(new CropCircleTransformation()).placeholder(R.drawable.prof_ico_1).into(img_user);

        holder.bind(status);

    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
