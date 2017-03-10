package REST.Model;

import org.parceler.Parcel;

import java.util.Date;
import java.util.jar.Attributes;

/**
 * Created by Anuj on 3/10/2017.
 */
@Parcel
public class League {
    private int LeagueId;
    private String Name;
    private int Competitor;
    private int TournamentId;
    private boolean Accepted;
    private int Creator;
    private Date CreateDate;

    public int getLeagueId(){return LeagueId;}
    public String getName() {return Name;}
    public int getCompetitor() {return Competitor;}
    public int getTournamentId() {return TournamentId;}
    public int getCreator() {return Creator;}
}
