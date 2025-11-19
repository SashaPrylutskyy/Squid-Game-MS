package com.sashaprylutskyy.squidgamems.repository;

import com.sashaprylutskyy.squidgamems.model.Round;
import com.sashaprylutskyy.squidgamems.model.User;
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
            @Param("roundId") Long roundId
    );

    @Query("""
            SELECT u FROM User u
            JOIN Assignment a ON a.user = u
            WHERE a.env = 'COMPETITION'
              AND a.envId = :competitionId
              AND u.status = 'ALIVE'
              AND u.id NOT IN (
                  SELECT rr.user.id
                  FROM RoundResult rr
                  WHERE rr.round = :round
              )
            """)
    List<User> findPlayersWithoutResultInRound(
            @Param("competitionId") Long competitionId,
            @Param("round") Round round
    );

    @Query("""
            SELECT u FROM User u
            JOIN Assignment lobbyA ON lobbyA.user = u
            WHERE u.role = :role
              AND u.status = :status
              AND lobbyA.env = 'LOBBY'
              AND lobbyA.envId = :lobbyId
              AND u.id NOT IN (
                  SELECT compA.user.id FROM Assignment compA
                  WHERE compA.env = 'COMPETITION'
              )
            """)
    List<User> findAvailablePlayersInLobby(@Param("lobbyId") Long lobbyId, @Param("role") Role role, @Param("status") UserStatus status);

    @Query("""
            SELECT u FROM User u
            JOIN Assignment lobbyA ON lobbyA.user = u
            WHERE u.role = :role
              AND u.status = :status
              AND lobbyA.env = 'LOBBY'
              AND lobbyA.envId = :lobbyId
              AND u.id IN (
                  SELECT compA.user.id FROM Assignment compA
                  WHERE compA.env = 'COMPETITION'
              )
            """)
    List<User> findAssignedPlayersInLobby(@Param("lobbyId") Long lobbyId, @Param("role") Role role, @Param("status") UserStatus status);

    @Query("""
            SELECT u FROM User u
            JOIN Assignment lobbyA ON lobbyA.user = u
            WHERE u.role = :role
              AND u.status = :status
              AND lobbyA.env = 'LOBBY'
              AND lobbyA.envId = :lobbyId
            """)
    List<User> findAllPlayersInLobby(@Param("lobbyId") Long lobbyId, @Param("role") Role role, @Param("status") UserStatus status);
}