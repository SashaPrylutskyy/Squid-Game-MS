package com.sashaprylutskyy.squidgamems.model.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class LoginRequestDTO {

    @NotBlank(message = "Email is missing")
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$", message = "Invalid email")
    private String email;

    @NotBlank(message = "Password is missing")
    @Size(min = 8, max = 32, message = "Password should be within 8-32 characters")
    private String password;

    public LoginRequestDTO() {

    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
