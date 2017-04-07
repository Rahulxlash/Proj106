package REST.ViewHolder;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cricketta.league.databinding.SelectPlayerBinding;

import REST.ViewModel.Player;

/**
 * Created by Anuj on 4/4/2017.
 */

public class PlayerViewHolder extends RecyclerView.ViewHolder {
    private SelectPlayerBinding binding;

    public PlayerViewHolder(View view) {
        super(view);
        binding = DataBindingUtil.bind(view);
    }

    public void bind(Player player) {
        binding.setPlayer(player);
    }

}

