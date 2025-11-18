package com.sashaprylutskyy.squidgamems.model.dto.round;

import com.sashaprylutskyy.squidgamems.model.enums.CompetitionRoundStatus;

public class RoundResponseDTO {

    private Long id;
    private String title;
    private Long competitionId;
    private Long gameId;
    private Byte roundNumber;
    private CompetitionRoundStatus status;
    private Long startedAt;
    private Long endedAt;

    public RoundResponseDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Long competitionId) {
        this.competitionId = competitionId;
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

    public CompetitionRoundStatus getStatus() {
        return status;
    }

    public void setStatus(CompetitionRoundStatus status) {
        this.status = status;
    }

    public Long getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Long startedAt) {
        this.startedAt = startedAt;
    }

    public Long getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(Long endedAt) {
        this.endedAt = endedAt;
    }
}
