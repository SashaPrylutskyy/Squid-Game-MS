package com.sashaprylutskyy.squidgamems.model.dto.transaction;

import com.sashaprylutskyy.squidgamems.model.dto.competition.CompetitionSummaryDTO;
import com.sashaprylutskyy.squidgamems.model.dto.user.UserSummaryDTO;
import com.sashaprylutskyy.squidgamems.model.enums.TransactionType;

public class TransactionResponseDTO {

    private Long id;
    private CompetitionSummaryDTO competition;
    private UserSummaryDTO sender;
    private UserSummaryDTO recipient;
    private int amount;
    private TransactionType transactionType;
    private Long createdAt;

    public TransactionResponseDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CompetitionSummaryDTO getCompetition() {
        return competition;
    }

    public void setCompetition(CompetitionSummaryDTO competition) {
        this.competition = competition;
    }

    public UserSummaryDTO getSender() {
        return sender;
    }

    public void setSender(UserSummaryDTO sender) {
        this.sender = sender;
    }

    public UserSummaryDTO getRecipient() {
        return recipient;
    }

    public void setRecipient(UserSummaryDTO recipient) {
        this.recipient = recipient;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}
