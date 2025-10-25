package com.sashaprylutskyy.squidgamems.model.dto.invitation;

import java.util.UUID;

public class InvitationResponseDTO {

    private UUID token;
    private String email;

    public InvitationResponseDTO() {

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
