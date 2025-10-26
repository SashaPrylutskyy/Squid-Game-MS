package com.sashaprylutskyy.squidgamems.model.dto.refCode;

import com.sashaprylutskyy.squidgamems.model.dto.user.UserSummaryDTO;

public class RefCodeSummaryDTO {

    private UserSummaryDTO user;
    private String refCode;

    public RefCodeSummaryDTO() {

    }

    public UserSummaryDTO getUser() {
        return user;
    }

    public void setUser(UserSummaryDTO user) {
        this.user = user;
    }

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }
}
