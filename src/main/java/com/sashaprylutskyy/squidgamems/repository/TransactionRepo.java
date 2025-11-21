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
                        @Param("transactionType") TransactionType transactionType);

        List<Transaction> findAllBySenderAndTransactionTypeOrderByCreatedAtDesc(
                        User sender,
                        TransactionType transactionType);

        @Query("""
                        SELECT COALESCE(SUM(t.amount), 0L)
                        FROM Transaction t
                        WHERE t.competition.id = :competitionId
                        AND t.transactionType = 'DEPOSIT'
                        AND t.sender.role = 'VIP'
                        """)
        Long getVipContributionsByCompetitionId(@Param("competitionId") Long competitionId);

        @Query("""
                        SELECT new com.sashaprylutskyy.squidgamems.model.dto.reports.VipStatsDTO(
                            t.sender.id,
                            t.sender.email,
                            SUM(t.amount),
                            CAST(COUNT(t) as int),
                            MAX(t.createdAt)
                        )
                        FROM Transaction t
                        WHERE t.sender.role = 'VIP'
                        AND t.transactionType = 'DEPOSIT'
                        GROUP BY t.sender.id, t.sender.email
                        ORDER BY SUM(t.amount) DESC
                        """)
        List<com.sashaprylutskyy.squidgamems.model.dto.reports.VipStatsDTO> getVipStats();
}
