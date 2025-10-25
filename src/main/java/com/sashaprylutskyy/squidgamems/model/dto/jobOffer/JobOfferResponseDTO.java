package com.sashaprylutskyy.squidgamems.model.dto.jobOffer;

import java.util.UUID;

public class JobOfferResponseDTO {

    private UUID token;
    private String email;

    public JobOfferResponseDTO() {

    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
