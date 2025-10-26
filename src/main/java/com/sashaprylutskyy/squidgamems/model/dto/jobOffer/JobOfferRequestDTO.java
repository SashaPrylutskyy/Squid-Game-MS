package com.sashaprylutskyy.squidgamems.model.dto.jobOffer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class JobOfferRequestDTO {

    @NotBlank(message = "Email is missing")
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$", message = "Invalid email")
    private String email;

    @NotNull(message = "RoleId is missing")
    private Long roleId;

    public JobOfferRequestDTO() {

    }

    public String getEmail() {
        return email;
    }

    public Long getRoleId() {
        return roleId;
    }
}
