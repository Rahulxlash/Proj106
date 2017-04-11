package com.cricketta.league.events;

import REST.ViewModel.ScoreCard;

/**
 * Created by rahul.sharma01 on 4/8/2017.
 */

public class PlayerSelectedEvent {

    public int playerId;
    public int matchId;
    public int leagueId;
    public int userId;
    public ScoreCard card;

    public PlayerSelectedEvent(int PlayerId, int MatchId, int LeagueId, int UserId, ScoreCard card) {
        this.playerId = PlayerId;
        this.matchId = MatchId;
        this.leagueId = LeagueId;
        this.userId = UserId;
        this.card = card;
    }
}
