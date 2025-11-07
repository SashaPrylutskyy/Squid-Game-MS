package com.sashaprylutskyy.squidgamems.model.dto.round;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class RoundRequestDTO {

    @NotNull
    private Long competitionId;

    @NotNull
    @Size(min = 1, message = "At least one round must be provided")
    private List<Long> gameIds;

    public RoundRequestDTO() {

    }

    public Long getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Long competitionId) {
        this.competitionId = competitionId;
    }

    public List<Long> getGameIds() {
        return gameIds;
    }

    public void setGameIds(List<Long> gameIds) {
        this.gameIds = gameIds;
    }
}
