package com.sashaprylutskyy.squidgamems.repository;

import com.sashaprylutskyy.squidgamems.model.Round;
import com.sashaprylutskyy.squidgamems.model.dto.reports.RoundStatsDTO;
import com.sashaprylutskyy.squidgamems.model.enums.CompetitionRoundStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoundRepo extends JpaRepository<Round, Long> {

    // --- ВИПРАВЛЕННЯ 1: Використовуємо правильний Enum та сортування ---
    @Query("""
            SELECT r FROM Round r
            WHERE r.competition.id = :competitionId
                AND r.startedAt IS NULL
                AND r.status = :status
            ORDER BY r.roundNumber ASC
            """)
    List<Round> findNextRounds(@Param("competitionId") Long competitionId,
                               @Param("status") CompetitionRoundStatus status);

    // --- ВИПРАВЛЕННЯ 2: Виправлено одрук у назві методу (findNextNextRounds -> findNextRounds) ---
    default List<Round> findNextRounds(Long competitionId) {
        return findNextRounds(competitionId, CompetitionRoundStatus.PENDING);
    }

    Optional<Round> findByIdAndCompetition_Id(Long id, Long competitionId);

    List<Round> findAllByCompetitionId(Long competitionId);

    @Query("SELECT r FROM Round r WHERE r.competition.id = :compId AND r.status = :status")
    Optional<Round> findActiveRoundByCompetitionId(@Param("compId") Long compId,
                                                   @Param("status") CompetitionRoundStatus status);

    // Зручний метод за замовчуванням
    default Optional<Round> findActiveRoundByCompetitionId(Long compId) {
        return findActiveRoundByCompetitionId(compId, CompetitionRoundStatus.ACTIVE);
    }

    // --- ВИПРАВЛЕННЯ 3: NULLIF(COUNT(rr), 0L) - додано 'L' до нуля, щоб збігалися типи з COUNT ---
    @Query("""
            SELECT new com.sashaprylutskyy.squidgamems.model.dto.reports.RoundStatsDTO(
                r.id,
                r.competition.id,
                CAST(r.roundNumber as int),
                r.game.gameTitle,
                r.startedAt,
                r.endedAt,
                (r.endedAt - r.startedAt),
                CAST(COUNT(rr) as int),
                CAST(SUM(CASE WHEN rr.status = 'ELIMINATED' THEN 1 ELSE 0 END) as int),
                (SUM(CASE WHEN rr.status = 'ELIMINATED' THEN 1.0 ELSE 0.0 END) / NULLIF(COUNT(rr), 0L))
            )
            FROM Round r
            LEFT JOIN RoundResult rr ON rr.round = r
            WHERE r.competition.id = :competitionId
            GROUP BY r.id, r.competition.id, r.roundNumber, r.game.gameTitle, r.startedAt, r.endedAt
            ORDER BY r.roundNumber ASC
            """)
    List<RoundStatsDTO> getRoundStatsByCompetitionId(@Param("competitionId") Long competitionId);

    // --- ТЕ Ж САМЕ ВИПРАВЛЕННЯ ДЛЯ 0L ---
    @Query("""
            SELECT new com.sashaprylutskyy.squidgamems.model.dto.reports.RoundStatsDTO(
                r.id,
                r.competition.id,
                CAST(r.roundNumber as int),
                r.game.gameTitle,
                r.startedAt,
                r.endedAt,
                (r.endedAt - r.startedAt),
                CAST(COUNT(rr) as int),
                CAST(SUM(CASE WHEN rr.status = 'ELIMINATED' THEN 1 ELSE 0 END) as int),
                (SUM(CASE WHEN rr.status = 'ELIMINATED' THEN 1.0 ELSE 0.0 END) / NULLIF(COUNT(rr), 0L))
            )
            FROM Round r
            LEFT JOIN RoundResult rr ON rr.round = r
            WHERE r.status = 'COMPLETED'
            GROUP BY r.id, r.competition.id, r.roundNumber, r.game.gameTitle, r.startedAt, r.endedAt
            ORDER BY (SUM(CASE WHEN rr.status = 'ELIMINATED' THEN 1.0 ELSE 0.0 END) / NULLIF(COUNT(rr), 0L)) DESC
            """)
    List<RoundStatsDTO> findTopHardestRounds(Pageable pageable);
}