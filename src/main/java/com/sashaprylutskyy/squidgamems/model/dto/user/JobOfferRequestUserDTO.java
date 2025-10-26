package com.sashaprylutskyy.squidgamems.model.dto.user;

import com.sashaprylutskyy.squidgamems.model.enums.Sex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

public class JobOfferRequestUserDTO {

    @NotBlank(message = "Password is missing")
    @Size(min = 8, max = 32, message = "Password should be within 8-32 characters")
    private String password;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 100)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 100)
    private String lastName;

    @NotBlank(message = "Profile photo is required")
    private String profilePhoto;

    @NotNull(message = "Select your sex/gender")
    private Sex sex;

    @NotNull(message = "Birthday is required")
    private Date birthday;

    public JobOfferRequestUserDTO() {

    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public Sex getSex() {
        return sex;
    }

    public Date getBirthday() {
        return birthday;
    }
}
