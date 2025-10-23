package com.sashaprylutskyy.squidgamems.model.dto;

import com.sashaprylutskyy.squidgamems.model.enums.Sex;
import com.sashaprylutskyy.squidgamems.model.enums.UserStatus;
import com.sashaprylutskyy.squidgamems.model.interfaceGroup.OnCreate;
import com.sashaprylutskyy.squidgamems.model.interfaceGroup.OnLogin;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;

import java.sql.Date;

public class UserRequestDTO {

    private Long id;

    @NotBlank(groups = {OnCreate.class, OnLogin.class})
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$", message = "Invalid email", groups = {OnCreate.class, OnLogin.class})
    private String email;

    @NotBlank(message = "Password is missing", groups = {OnCreate.class, OnLogin.class})
    @Size(min = 8, max = 32, message = "Password should be within 8-32 characters", groups = {OnCreate.class, OnLogin.class})
    private String password;

    @NotBlank(message = "First name is required", groups = OnCreate.class)
    @Size(min = 2, max = 100, groups = OnCreate.class)
    private String firstName;

    @NotBlank(message = "Last name is required", groups = OnCreate.class)
    @Size(min = 2, max = 100, groups = OnCreate.class)
    private String lastName;

    @NotBlank(message = "Profile photo is required", groups = OnCreate.class)
    private String profilePhoto;

    @NotNull(message = "Select your sex/gender", groups = OnCreate.class)
    private Sex sex;

    @NotNull(message = "Birthday is required", groups = OnCreate.class)
    private Date birthday;

    private Long balance;

    @NotNull(message = "Role is required", groups = OnCreate.class)
    private Long roleId;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    public UserRequestDTO() {}

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
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

    public Long getBalance() {
        return balance;
    }

    public Long getRoleId() {
        return roleId;
    }

    public UserStatus getStatus() {
        return status;
    }
}
