package com.sashaprylutskyy.squidgamems.model.dto.vote;

import com.sashaprylutskyy.squidgamems.model.dto.user.UserSummaryDTO;

public class VoteResponseDTO {

    private UserSummaryDTO user;
    private Boolean isQuit;

    public VoteResponseDTO() {

    }

    public UserSummaryDTO getUser() {
        return user;
    }

    public void setUser(UserSummaryDTO user) {
        this.user = user;
    }

    public Boolean getQuit() {
        return isQuit;
    }

    public void setQuit(Boolean quit) {
        isQuit = quit;
    }
}
