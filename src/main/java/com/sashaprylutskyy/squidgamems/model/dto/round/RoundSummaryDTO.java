package com.sashaprylutskyy.squidgamems.model.dto.round;

public class RoundSummaryDTO {

    private Long id;
    private Long gameId;
    private Byte roundNumber;

    public RoundSummaryDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Byte getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(Byte roundNumber) {
        this.roundNumber = roundNumber;
    }
}
