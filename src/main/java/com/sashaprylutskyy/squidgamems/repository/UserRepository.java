package com.sashaprylutskyy.squidgamems.repository;

import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.enums.Env;
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

    // --- ✨ НОВІ МЕТОДИ ---

    /**
     * Знаходить "вільних" гравців:
     * 1. Прикріплені до конкретного Лобі.
     * 2. Живі.
     * 3. НЕ мають активного запису в Assignment з Env.COMPETITION.
     */
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
    List<User> findAvailablePlayersInLobby(
            @Param("lobbyId") Long lobbyId,
            @Param("role") Role role,
            @Param("status") UserStatus status
    );

    /**
     * Знаходить гравців, які вже беруть участь у змаганні.
     */
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
    List<User> findAssignedPlayersInLobby(
            @Param("lobbyId") Long lobbyId,
            @Param("role") Role role,
            @Param("status") UserStatus status
    );

    /**
     * Отримати всіх живих гравців у лобі (незалежно від того, зайняті вони чи ні)
     */
    @Query("""
            SELECT u FROM User u
            JOIN Assignment lobbyA ON lobbyA.user = u
            WHERE u.role = :role
              AND u.status = :status
              AND lobbyA.env = 'LOBBY'
              AND lobbyA.envId = :lobbyId
            """)
    List<User> findAllPlayersInLobby(
            @Param("lobbyId") Long lobbyId,
            @Param("role") Role role,
            @Param("status") UserStatus status
    );
}