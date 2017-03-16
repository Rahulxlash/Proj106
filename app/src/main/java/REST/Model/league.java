package REST.Model;

import org.parceler.Parcel;

import java.util.Date;
import java.util.jar.Attributes;

/**
 * Created by Anuj on 3/10/2017.
 */
@Parcel
public class League {
    private int leagueId;
    private String name;
    private int competitor;
    private int tournamentId;
    private boolean accepted;
    private int creator;
    private Date createDate;

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
}
