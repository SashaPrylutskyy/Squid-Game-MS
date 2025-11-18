package com.sashaprylutskyy.squidgamems.model.dto.user;

import com.sashaprylutskyy.squidgamems.model.enums.UserStatus;

public class PlayerReportDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private UserStatus reportedStatus;

    public PlayerReportDTO(Long id, String firstName, String lastName, UserStatus reportedStatus) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.reportedStatus = reportedStatus;
    }

    public PlayerReportDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserStatus getReportedStatus() {
        return reportedStatus;
    }

    public void setReportedStatus(UserStatus reportedStatus) {
        this.reportedStatus = reportedStatus;
    }
}
