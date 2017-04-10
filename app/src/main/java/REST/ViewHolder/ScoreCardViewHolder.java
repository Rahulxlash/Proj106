package REST.ViewHolder;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cricketta.league.databinding.TeamPlayerBinding;

import REST.ViewModel.ScoreCard;

/**
 * Created by rahul.sharma01 on 4/10/2017.
 */

public class ScoreCardViewHolder extends RecyclerView.ViewHolder {
    private TeamPlayerBinding binding;

    public ScoreCardViewHolder(View view) {
        super(view);
        binding = DataBindingUtil.bind(view);
    }

    public void bind(ScoreCard player) {
        binding.setPlayer(player);
    }

}

