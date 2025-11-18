package com.sashaprylutskyy.squidgamems.repository;

import com.sashaprylutskyy.squidgamems.model.Competition;
import com.sashaprylutskyy.squidgamems.model.enums.CompetitionRoundStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompetitionRepo extends JpaRepository<Competition, Long> {

    List<Competition> findAllByLobbyId(Long lobbyId);

    @Query("""
            SELECT c FROM Competition c
            WHERE c.lobbyId = :lobbyId
            AND c.status = 'ACTIVE'
            """)
    Optional<Competition> findActiveByLobbyId(@Param("lobbyId") Long lobbyId);

    List<Competition> findAllByStatusIn(List<CompetitionRoundStatus> statuses);
}
