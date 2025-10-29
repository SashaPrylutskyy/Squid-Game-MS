package com.sashaprylutskyy.squidgamems.model.dto.jobOffer;

import com.sashaprylutskyy.squidgamems.model.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class JobOfferRequestDTO {

    @NotBlank(message = "Email is missing")
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$", message = "Invalid email")
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    public JobOfferRequestDTO() {

    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }
}
