package REST.ViewHolder;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cricketta.league.databinding.MatchSummaryCardBinding;

import REST.Model.LeagueMatch;

/**
 * Created by rahul.sharma01 on 3/23/2017.
 */

public class MatchViewHolder extends RecyclerView.ViewHolder {
    private MatchSummaryCardBinding binding;

    public MatchViewHolder(View view) {
        super(view);
        binding = DataBindingUtil.bind(view);
    }

    public void bind(LeagueMatch match) {
        binding.setMatch(match);
    }

}