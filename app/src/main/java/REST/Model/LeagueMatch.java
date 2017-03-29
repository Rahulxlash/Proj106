package REST.Model;

import org.parceler.Parcel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rahul.sharma01 on 3/23/2017.
 */
@Parcel
public class LeagueMatch {
    public int leagueMatchId;
    public int matchId;
    public int leagueId;
    public int teamId1;
    public int teamId2;
    public boolean tossDone;
    public int toss;
    public int creatorPoint;
    public int competitorPoint;
    public Date matchDate;
    public String venue;
    public String teamName1;
    public String teamName2;
    public int creatorRun;
    public int creatorWicket;
    public int competitorRun;
    public int competitorWicket;
    public int tossRequestedBy;

    public String getTeams() {
        return teamName1.trim() + "-" + teamName2.trim();
    }

    public String getMatchDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String DateToStr = format.format(matchDate);
        return DateToStr;
    }

    public String getMyPoint() {
        return Integer.toString(creatorPoint - competitorPoint);
    }
}
