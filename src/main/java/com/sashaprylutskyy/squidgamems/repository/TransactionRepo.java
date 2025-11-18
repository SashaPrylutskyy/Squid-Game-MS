package com.sashaprylutskyy.squidgamems.repository;

import com.sashaprylutskyy.squidgamems.model.Transaction;
import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {

    @Query("""
            SELECT COALESCE(SUM(t.amount), 0L)
            FROM Transaction t
            WHERE t.competition.id = :competitionId
            AND t.transactionType = :transactionType
            """)
    Long getTotalAmountByCompetitionIdAndType(
            @Param("competitionId") Long competitionId,
            @Param("transactionType") TransactionType transactionType
    );

    List<Transaction> findAllBySenderAndTransactionTypeOrderByCreatedAtDesc(
            User sender,
            TransactionType transactionType
    );

}
