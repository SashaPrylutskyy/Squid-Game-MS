package com.sashaprylutskyy.squidgamems.model.dto.transaction;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class TransactionDepositRequestDTO {

    @NotNull(message = "Enter a competition id")
    private Long competitionId;

    @Min(value = 1, message = "Enter a transaction's amount")
    private int amount;

    public TransactionDepositRequestDTO() {

    }

    public Long getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Long competitionId) {
        this.competitionId = competitionId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
