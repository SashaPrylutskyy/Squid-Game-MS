package com.sashaprylutskyy.squidgamems.model.dto.reports;

import java.util.List;

public class CompetitionStatsDTO {
    private Long competitionId;
    private long totalPrizePool;
    private long vipContributions;
    private List<RoundStatsDTO> rounds;

    public CompetitionStatsDTO() {
    }

    public CompetitionStatsDTO(Long competitionId, long totalPrizePool, long vipContributions,
            List<RoundStatsDTO> rounds) {
        this.competitionId = competitionId;
        this.totalPrizePool = totalPrizePool;
        this.vipContributions = vipContributions;
        this.rounds = rounds;
    }

    public Long getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Long competitionId) {
        this.competitionId = competitionId;
    }

    public long getTotalPrizePool() {
        return totalPrizePool;
    }

    public void setTotalPrizePool(long totalPrizePool) {
        this.totalPrizePool = totalPrizePool;
    }

    public long getVipContributions() {
        return vipContributions;
    }

    public void setVipContributions(long vipContributions) {
        this.vipContributions = vipContributions;
    }

    public List<RoundStatsDTO> getRounds() {
        return rounds;
    }

    public void setRounds(List<RoundStatsDTO> rounds) {
        this.rounds = rounds;
    }
}
