package com.sashaprylutskyy.squidgamems.model.dto.reports;

public class StaffStatsDTO {
    private Long workerId;
    private String email;
    private int reportsSubmitted;
    private double avgConfirmationTimeSeconds;
    private int rejectedReports;

    public StaffStatsDTO() {
    }

    public StaffStatsDTO(Long workerId, String email, int reportsSubmitted, double avgConfirmationTimeSeconds,
            int rejectedReports) {
        this.workerId = workerId;
        this.email = email;
        this.reportsSubmitted = reportsSubmitted;
        this.avgConfirmationTimeSeconds = avgConfirmationTimeSeconds;
        this.rejectedReports = rejectedReports;
    }

    public Long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Long workerId) {
        this.workerId = workerId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getReportsSubmitted() {
        return reportsSubmitted;
    }

    public void setReportsSubmitted(int reportsSubmitted) {
        this.reportsSubmitted = reportsSubmitted;
    }

    public double getAvgConfirmationTimeSeconds() {
        return avgConfirmationTimeSeconds;
    }

    public void setAvgConfirmationTimeSeconds(double avgConfirmationTimeSeconds) {
        this.avgConfirmationTimeSeconds = avgConfirmationTimeSeconds;
    }

    public int getRejectedReports() {
        return rejectedReports;
    }

    public void setRejectedReports(int rejectedReports) {
        this.rejectedReports = rejectedReports;
    }
}
