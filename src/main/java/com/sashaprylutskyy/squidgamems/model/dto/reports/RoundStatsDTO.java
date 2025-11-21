package com.sashaprylutskyy.squidgamems.model.dto.reports;

public class RoundStatsDTO {
    private Long roundId;
    private Long competitionId;
    private Integer roundNumber;
    private String gameTitle;
    private Long startedAt;
    private Long endedAt;
    private Long durationSeconds;
    private Integer playersStarted;
    private Integer playersEliminated;
    private Double eliminationRate;
    private Double avgSurvivalTimeSeconds;

    public RoundStatsDTO() {
    }

    public RoundStatsDTO(Long roundId, Long competitionId, Integer roundNumber, String gameTitle, Long startedAt,
            Long endedAt, Long durationSeconds, Integer playersStarted, Integer playersEliminated,
            Double eliminationRate) {
        this.roundId = roundId;
        this.competitionId = competitionId;
        this.roundNumber = roundNumber;
        this.gameTitle = gameTitle;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.durationSeconds = durationSeconds;
        this.playersStarted = playersStarted;
        this.playersEliminated = playersEliminated;
        this.eliminationRate = eliminationRate;
    }

    public Long getRoundId() {
        return roundId;
    }

    public void setRoundId(Long roundId) {
        this.roundId = roundId;
    }

    public Long getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Long competitionId) {
        this.competitionId = competitionId;
    }

    public Integer getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(Integer roundNumber) {
        this.roundNumber = roundNumber;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
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

    public Long getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(Long durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public Integer getPlayersStarted() {
        return playersStarted;
    }

    public void setPlayersStarted(Integer playersStarted) {
        this.playersStarted = playersStarted;
    }

    public Integer getPlayersEliminated() {
        return playersEliminated;
    }

    public void setPlayersEliminated(Integer playersEliminated) {
        this.playersEliminated = playersEliminated;
    }

    public Double getEliminationRate() {
        return eliminationRate;
    }

    public void setEliminationRate(Double eliminationRate) {
        this.eliminationRate = eliminationRate;
    }

    public Double getAvgSurvivalTimeSeconds() {
        return avgSurvivalTimeSeconds;
    }

    public void setAvgSurvivalTimeSeconds(Double avgSurvivalTimeSeconds) {
        this.avgSurvivalTimeSeconds = avgSurvivalTimeSeconds;
    }
}
