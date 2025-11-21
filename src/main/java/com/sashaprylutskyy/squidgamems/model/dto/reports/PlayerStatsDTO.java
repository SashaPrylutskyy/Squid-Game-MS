package com.sashaprylutskyy.squidgamems.model.dto.reports;

public class PlayerStatsDTO {
    private Long userId;
    private String email;
    private int roundsPassed;
    private int competitionsEntered;
    private double avgLifespanSeconds;
    private Integer fastestEliminationRound;
    private double confirmationRate;

    public PlayerStatsDTO() {
    }

    public PlayerStatsDTO(Long userId, String email, int roundsPassed, int competitionsEntered,
            double avgLifespanSeconds, Integer fastestEliminationRound, double confirmationRate) {
        this.userId = userId;
        this.email = email;
        this.roundsPassed = roundsPassed;
        this.competitionsEntered = competitionsEntered;
        this.avgLifespanSeconds = avgLifespanSeconds;
        this.fastestEliminationRound = fastestEliminationRound;
        this.confirmationRate = confirmationRate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRoundsPassed() {
        return roundsPassed;
    }

    public void setRoundsPassed(int roundsPassed) {
        this.roundsPassed = roundsPassed;
    }

    public int getCompetitionsEntered() {
        return competitionsEntered;
    }

    public void setCompetitionsEntered(int competitionsEntered) {
        this.competitionsEntered = competitionsEntered;
    }

    public double getAvgLifespanSeconds() {
        return avgLifespanSeconds;
    }

    public void setAvgLifespanSeconds(double avgLifespanSeconds) {
        this.avgLifespanSeconds = avgLifespanSeconds;
    }

    public Integer getFastestEliminationRound() {
        return fastestEliminationRound;
    }

    public void setFastestEliminationRound(Integer fastestEliminationRound) {
        this.fastestEliminationRound = fastestEliminationRound;
    }

    public double getConfirmationRate() {
        return confirmationRate;
    }

    public void setConfirmationRate(double confirmationRate) {
        this.confirmationRate = confirmationRate;
    }
}
