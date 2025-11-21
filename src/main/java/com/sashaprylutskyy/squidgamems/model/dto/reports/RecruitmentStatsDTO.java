package com.sashaprylutskyy.squidgamems.model.dto.reports;

public class RecruitmentStatsDTO {
    private Long salesmanId;
    private String email;
    private String refCode;
    private int playersRecruited;
    private int activePlayers;

    public RecruitmentStatsDTO() {
    }

    public RecruitmentStatsDTO(Long salesmanId, String email, String refCode, int playersRecruited, int activePlayers) {
        this.salesmanId = salesmanId;
        this.email = email;
        this.refCode = refCode;
        this.playersRecruited = playersRecruited;
        this.activePlayers = activePlayers;
    }

    public Long getSalesmanId() {
        return salesmanId;
    }

    public void setSalesmanId(Long salesmanId) {
        this.salesmanId = salesmanId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    public int getPlayersRecruited() {
        return playersRecruited;
    }

    public void setPlayersRecruited(int playersRecruited) {
        this.playersRecruited = playersRecruited;
    }

    public int getActivePlayers() {
        return activePlayers;
    }

    public void setActivePlayers(int activePlayers) {
        this.activePlayers = activePlayers;
    }
}
