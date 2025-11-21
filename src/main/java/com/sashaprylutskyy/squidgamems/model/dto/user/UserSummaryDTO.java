package com.sashaprylutskyy.squidgamems.model.dto.user;

import com.sashaprylutskyy.squidgamems.model.enums.Role;
import com.sashaprylutskyy.squidgamems.model.enums.Sex;
import com.sashaprylutskyy.squidgamems.model.enums.UserStatus;

import java.util.Date;

public class UserSummaryDTO {

    private Long id;
    private String email;
    private Role role;
    private UserStatus status;
    private Sex sex;
    private Date birthday;
    private Long createdAt;

    public UserSummaryDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}
