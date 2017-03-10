package REST.ViewHolder;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cricketta.league.databinding.ItemLeagueListBinding;

import REST.Model.League;

/**
 * Created by Anuj on 3/10/2017.
 */

public class LeagueViewHolder extends RecyclerView.ViewHolder {
    private ItemLeagueListBinding binding;

    public LeagueViewHolder(View view) {
        super(view);
        binding = DataBindingUtil.bind(view);
    }

    public void bind(League league) {
        binding.setLeague(league);
    }

}