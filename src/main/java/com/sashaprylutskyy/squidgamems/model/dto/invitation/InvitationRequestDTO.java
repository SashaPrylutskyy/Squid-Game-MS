package com.sashaprylutskyy.squidgamems.model.dto.invitation;

import com.sashaprylutskyy.squidgamems.model.interfaceGroup.OnCreate;
import com.sashaprylutskyy.squidgamems.model.interfaceGroup.OnLogin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class InvitationRequestDTO {

    @NotBlank(groups = {OnCreate.class, OnLogin.class})
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$", message = "Invalid email")
    private String email;

    @NotNull
    private Long roleId;

    public InvitationRequestDTO() {

    }

    public String getEmail() {
        return email;
    }

    public Long getRoleId() {
        return roleId;
    }
}
