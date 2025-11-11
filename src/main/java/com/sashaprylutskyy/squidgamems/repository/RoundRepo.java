package com.sashaprylutskyy.squidgamems.repository;

import com.sashaprylutskyy.squidgamems.model.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoundRepo extends JpaRepository<Round, Long> {

    @Query( """
            SELECT r FROM Round r
            WHERE r.competition.id = :competitionId
                AND r.startedAt IS NULL
            ORDER BY r.roundNumber ASC
            """)
    List<Round> findNextRounds(@Param("competitionId") Long competitionId);

    Optional<Round> findByIdAndCompetition_Id(Long id, Long competitionId);
}
