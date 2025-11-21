package com.sashaprylutskyy.squidgamems.model.dto.reports;

public class VipStatsDTO {
    private Long vipId;
    private String email;
    private long totalContributed;
    private int depositCount;
    private Long lastDepositAt;

    public VipStatsDTO() {
    }

    public VipStatsDTO(Long vipId, String email, long totalContributed, int depositCount, Long lastDepositAt) {
        this.vipId = vipId;
        this.email = email;
        this.totalContributed = totalContributed;
        this.depositCount = depositCount;
        this.lastDepositAt = lastDepositAt;
    }

    public Long getVipId() {
        return vipId;
    }

    public void setVipId(Long vipId) {
        this.vipId = vipId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getTotalContributed() {
        return totalContributed;
    }

    public void setTotalContributed(long totalContributed) {
        this.totalContributed = totalContributed;
    }

    public int getDepositCount() {
        return depositCount;
    }

    public void setDepositCount(int depositCount) {
        this.depositCount = depositCount;
    }

    public Long getLastDepositAt() {
        return lastDepositAt;
    }

    public void setLastDepositAt(Long lastDepositAt) {
        this.lastDepositAt = lastDepositAt;
    }
}
