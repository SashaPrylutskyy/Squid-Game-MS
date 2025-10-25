package com.sashaprylutskyy.squidgamems.model.dto.jobOffer;

import com.sashaprylutskyy.squidgamems.model.Role;
import com.sashaprylutskyy.squidgamems.model.dto.user.UserSummaryDTO;
import com.sashaprylutskyy.squidgamems.model.enums.JobOfferStatus;

public class JobOfferSummaryDTO {

    private String email;
    private Role role;
    private JobOfferStatus offerStatus;
    private Long lobbyId;
    private UserSummaryDTO offeredBy;

    public JobOfferSummaryDTO() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public JobOfferStatus getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(JobOfferStatus offerStatus) {
        this.offerStatus = offerStatus;
    }

    public Long getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(Long lobbyId) {
        this.lobbyId = lobbyId;
    }

    public UserSummaryDTO getOfferedBy() {
        return offeredBy;
    }

    public void setOfferedBy(UserSummaryDTO offeredBy) {
        this.offeredBy = offeredBy;
    }
}
