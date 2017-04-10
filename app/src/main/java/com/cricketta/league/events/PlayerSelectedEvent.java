package com.cricketta.league.events;

/**
 * Created by rahul.sharma01 on 4/8/2017.
 */

public class PlayerSelectedEvent {

    public int playerId;
    public int matchId;
    public int leagueId;
    public int userId;

    public PlayerSelectedEvent(int PlayerId, int MatchId, int LeagueId, int UserId) {
        this.playerId = PlayerId;
        this.matchId = MatchId;
        this.leagueId = LeagueId;
        this.userId = UserId;
    }
}
