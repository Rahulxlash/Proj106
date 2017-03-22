package REST.Model;

import android.databinding.BindingAdapter;
import android.view.View;

import com.cricketta.league.R;

import org.parceler.Parcel;

import java.util.Date;

/**
 * Created by Anuj on 3/10/2017.
 */
@Parcel
public class League {
    private int leagueId;
    private String name;
    private int competitor;
    private int tournamentId;
    private int accepted;
    private int creator;
    private Date createDate;
    private String competitorName;
    private boolean isMyLeague;
    private int points;

    @BindingAdapter("android:visibility")
    public static void setVisibility(View view, League model) {
        view.setVisibility(model.Accepted() ? View.VISIBLE : View.INVISIBLE);
    }

    public int getLeagueId() {
        return leagueId;
    }

    public String getName() {
        return name;
    }

    public int getCompetitor() {
        return competitor;
    }

    public int getTournamentId() {
        return tournamentId;
    }

    public int getCreator() {
        return creator;
    }

    public boolean Accepted() {
        return accepted != 0;
    }

    public char getLeagueTitle() {
        return name.toUpperCase().charAt(0);
    }

    public String getCompetitorName() {
        return competitorName;
    }

    public boolean getIsMyLeague() {
        return isMyLeague;
    }

    public int getPoints() {
        return points;
    }

    public boolean showAccept() {
        return accepted == 0 && isMyLeague == false;
    }

    public int getCardColor() {

        if (accepted == 0)
            return R.color.cardcolor;
        if (accepted == 2)
            return R.color.cardcolorReject;
        if (points > 0)
            return R.color.cardcolorWin;
        else
            return R.color.cardcolorLoss;
    }

    public boolean showPending() {
        return accepted == 0 && isMyLeague == true;
    }
}