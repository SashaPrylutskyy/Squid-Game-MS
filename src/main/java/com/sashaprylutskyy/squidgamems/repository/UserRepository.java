package com.sashaprylutskyy.squidgamems.repository;

import com.sashaprylutskyy.squidgamems.model.Round;
import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.dto.reports.PlayerStatsDTO;
import com.sashaprylutskyy.squidgamems.model.dto.user.PlayerReportDTO;
import com.sashaprylutskyy.squidgamems.model.enums.Role;
import com.sashaprylutskyy.squidgamems.model.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserById(Long id);

    // Тут логіка хороша, залишаємо як є
    @Query("""
            SELECT new com.sashaprylutskyy.squidgamems.model.dto.user.PlayerReportDTO(
                u.id,
                u.firstName,
                u.lastName,
                rr.status
            )
            FROM User u
            JOIN Assignment a ON a.user = u
            LEFT JOIN RoundResult rr ON rr.user = u AND rr.round.id = :roundId
            WHERE a.env = 'COMPETITION'
              AND a.envId = :competitionId
              AND u.role = 'PLAYER'
              AND u.status = 'ALIVE'
            ORDER BY u.lastName, u.firstName
            """)
    List<PlayerReportDTO> findPlayersWithRoundStatus(
            @Param("competitionId") Long competitionId,
            @Param("roundId") Long roundId);

    // OPTIMIZATION: Заміна 'NOT IN' на 'NOT EXISTS' для швидкодії
    @Query("""
            SELECT u FROM User u
            JOIN Assignment a ON a.user = u
            WHERE a.env = 'COMPETITION'
              AND a.envId = :competitionId
              AND u.status = 'ALIVE'
              AND NOT EXISTS (
                  SELECT 1 FROM RoundResult rr
                  WHERE rr.user = u AND rr.round = :round
              )
            """)
    List<User> findPlayersWithoutResultInRound(
            @Param("competitionId") Long competitionId,
            @Param("round") Round round);

    // OPTIMIZATION: Заміна 'NOT IN' на 'NOT EXISTS'
    @Query("""
            SELECT u FROM User u
            JOIN Assignment lobbyA ON lobbyA.user = u
            WHERE u.role = :role
              AND u.status = :status
              AND lobbyA.env = 'LOBBY'
              AND lobbyA.envId = :lobbyId
              AND NOT EXISTS (
                  SELECT 1 FROM Assignment compA
                  WHERE compA.user = u
                  AND compA.env = 'COMPETITION'
              )
            """)
    List<User> findAvailablePlayersInLobby(@Param("lobbyId") Long lobbyId,
                                           @Param("role") Role role,
                                           @Param("status") UserStatus status);

    // OPTIMIZATION: Заміна 'IN (Subquery)' на 'JOIN' (більш ефективно)
    @Query("""
            SELECT DISTINCT u FROM User u
            JOIN Assignment lobbyA ON lobbyA.user = u
            JOIN Assignment compA ON compA.user = u
            WHERE u.role = :role
              AND u.status = :status
              AND lobbyA.env = 'LOBBY'
              AND lobbyA.envId = :lobbyId
              AND compA.env = 'COMPETITION'
            """)
    List<User> findAssignedPlayersInLobby(@Param("lobbyId") Long lobbyId,
                                          @Param("role") Role role,
                                          @Param("status") UserStatus status);


    @Query("""
            SELECT u FROM User u
            JOIN Assignment lobbyA ON lobbyA.user = u
            WHERE u.role = :role
              AND u.status = :status
              AND lobbyA.env = 'LOBBY'
              AND lobbyA.envId = :lobbyId
            """)
    List<User> findAllPlayersInLobby(@Param("lobbyId") Long lobbyId,
                                     @Param("role") Role role,
                                     @Param("status") UserStatus status);

    /**
     * CRITICAL OPTIMIZATION:
     * 1. Замінено 3 підзапити на LEFT JOIN + Aggregation (GROUP BY).
     * 2. SUM(CASE WHEN...) дозволяє рахувати умови без зайвих запитів.
     * 3. COALESCE обробляє NULL, якщо у користувача немає ігор.
     */
    @Query("""
            SELECT new com.sashaprylutskyy.squidgamems.model.dto.reports.PlayerStatsDTO(
                u.id,
                u.email,
                CAST(COALESCE(SUM(CASE WHEN rr.status = 'PASSED' THEN 1 ELSE 0 END), 0) as int),
                CAST(COUNT(DISTINCT r.competition) as int),
                0.0,
                CAST(MIN(CASE WHEN rr.status = 'ELIMINATED' THEN r.roundNumber ELSE NULL END) as int),
                0.0
            )
            FROM User u
            LEFT JOIN RoundResult rr ON rr.user = u
            LEFT JOIN rr.round r
            WHERE u.role = 'PLAYER'
            GROUP BY u.id, u.email
            """)
    List<PlayerStatsDTO> getPlayerStatistics();
}