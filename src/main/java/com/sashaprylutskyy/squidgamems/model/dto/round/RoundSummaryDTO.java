package com.sashaprylutskyy.squidgamems.model.dto.round;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class RoundSummaryDTO {

    @NotNull(message = "gameId is required")
    private Long gameId;

    @NotNull
    @Min(value = 1, message = "Min round number is 1")
    @Max(value = 1, message = "Max round number is 7")
    private Byte roundNumber;

    public RoundSummaryDTO() {

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
