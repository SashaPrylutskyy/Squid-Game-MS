package com.sashaprylutskyy.squidgamems.model.dto;

import com.sashaprylutskyy.squidgamems.model.enums.Sex;
import com.sashaprylutskyy.squidgamems.model.enums.UserStatus;

import java.util.Date;

public class UserResponseDTO {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String profilePhoto;
    private Sex sex;
    private String roleTitle;
    private Date birthday;
    private Long balance;
    private UserStatus status;
    private Long createdAt;
    private Long updatedAt;

    public UserResponseDTO() {}

    public UserResponseDTO(Long id, String email, String firstName, String lastName,
                           String profilePhoto, Sex sex, String roleTitle, Date birthday,
                           Long balance, UserStatus status, Long createdAt, Long updatedAt) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePhoto = profilePhoto;
        this.sex = sex;
        this.roleTitle = roleTitle;
        this.birthday = birthday;
        this.balance = balance;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
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

    public String getRoleTitle() {
        return roleTitle;
    }

    public Date getBirthday() {
        return birthday;
    }

    public Long getBalance() {
        return balance;
    }

    public UserStatus getStatus() {
        return status;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }
}
