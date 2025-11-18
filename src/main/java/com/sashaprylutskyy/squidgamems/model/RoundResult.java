package com.sashaprylutskyy.squidgamems.model;

import com.sashaprylutskyy.squidgamems.model.enums.UserStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "round_results",
       uniqueConstraints = {
           @UniqueConstraint(
               name = "uk_round_user",
               columnNames = {"round_id", "user_id"}
           )
       }
)
public class RoundResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "round_id")
    private Round round;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 25)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private Long reportedAt;
    private Long confirmedAt;

    @ManyToOne
    @JoinColumn(name = "reported_by")
    private User reportedBy;

    @ManyToOne
    @JoinColumn(name = "confirmed_by")
    private User confirmedBy;

    public RoundResult() {

    }

    public RoundResult(Round round, User user, UserStatus status, Long reportedAt, User reportedBy) {
        this.round = round;
        this.user = user;
        this.status = status;
        this.reportedAt = reportedAt;
        this.reportedBy = reportedBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Long getReportedAt() {
        return reportedAt;
    }

    public void setReportedAt(Long reportedAt) {
        this.reportedAt = reportedAt;
    }

    public Long getConfirmedAt() {
        return confirmedAt;
    }

    public void setConfirmedAt(Long confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    public User getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(User reportedBy) {
        this.reportedBy = reportedBy;
    }

    public User getConfirmedBy() {
        return confirmedBy;
    }

    public void setConfirmedBy(User confirmedBy) {
        this.confirmedBy = confirmedBy;
    }
}

