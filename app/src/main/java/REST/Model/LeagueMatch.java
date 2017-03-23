package REST.Model;

import org.parceler.Parcel;

import java.util.Date;

/**
 * Created by rahul.sharma01 on 3/23/2017.
 */
@Parcel
public class LeagueMatch {
    private int leagueMatchId;
    private int matchId;
    private int leagueId;
    private int teamId1;
    private int teamId2;
    private boolean tossDone;
    private int toss;
    private int creatorPoint;
    private int competitorPoint;
    private Date matchDate;
    private String venue;
    private String teamName1;
    private String teamName2;
}
