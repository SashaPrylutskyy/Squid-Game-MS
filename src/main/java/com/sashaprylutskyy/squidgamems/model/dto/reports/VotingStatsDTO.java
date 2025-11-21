package com.sashaprylutskyy.squidgamems.model.dto.reports;

import java.util.Map;

public class VotingStatsDTO {
    private long totalVotes;
    private double quitPercentage;
    private double continuePercentage;
    private Map<String, Map<String, Integer>> byDemographics;

    public VotingStatsDTO() {
    }

    public VotingStatsDTO(long totalVotes, double quitPercentage, double continuePercentage,
            Map<String, Map<String, Integer>> byDemographics) {
        this.totalVotes = totalVotes;
        this.quitPercentage = quitPercentage;
        this.continuePercentage = continuePercentage;
        this.byDemographics = byDemographics;
    }

    public long getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(long totalVotes) {
        this.totalVotes = totalVotes;
    }

    public double getQuitPercentage() {
        return quitPercentage;
    }

    public void setQuitPercentage(double quitPercentage) {
        this.quitPercentage = quitPercentage;
    }

    public double getContinuePercentage() {
        return continuePercentage;
    }

    public void setContinuePercentage(double continuePercentage) {
        this.continuePercentage = continuePercentage;
    }

    public Map<String, Map<String, Integer>> getByDemographics() {
        return byDemographics;
    }

    public void setByDemographics(Map<String, Map<String, Integer>> byDemographics) {
        this.byDemographics = byDemographics;
    }
}
