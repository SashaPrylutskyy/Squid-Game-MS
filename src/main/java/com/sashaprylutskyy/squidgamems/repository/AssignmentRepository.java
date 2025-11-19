package com.sashaprylutskyy.squidgamems.repository;

import com.sashaprylutskyy.squidgamems.model.Assignment;
import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.enums.Env;
import com.sashaprylutskyy.squidgamems.model.enums.Sex;
import com.sashaprylutskyy.squidgamems.model.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    @Query("""
            SELECT a FROM Assignment a
            WHERE a.env =:env
            AND a.envId =:envId
            AND a.user.id =:userId
            """)
    Assignment findBy(
            @Param("env") Env env,
            @Param("envId") Long envId,
            @Param("userId") Long userId
    );

    @Query("""
            SELECT a FROM Assignment a
            JOIN Competition c ON a.envId = c.id
            WHERE a.env = 'COMPETITION'
            AND a.user = :user
            AND c.status = 'ACTIVE'
            """)
    Optional<Assignment> findAssignmentUserInActiveCompetition(@Param("user") User user);

    Optional<Assignment> findAssignmentByEnvAndUser(Env env, User user);

    @Query("""
            SELECT a FROM Assignment a
            WHERE a.env = :env
            AND a.envId = :envId
            """)
    List<Assignment> findAssignments(
            @Param("env") Env env,
            @Param("envId") Long envId);

    @Query("""
            SELECT a FROM Assignment a
            WHERE a.env = :env
            AND a.envId = :envId
            AND a.user.status = :status
            """)
    List<Assignment> findAssignments(
            @Param("env") Env env,
            @Param("envId") Long envId,
            @Param("status") UserStatus status);

    @Query("""
            SELECT a FROM Assignment a
            WHERE a.env = :env
            AND a.envId = :envId
            AND a.user.status = :status
            AND a.user.sex = :sex
            """)
    List<Assignment> findAssignments(
            @Param("env") Env env,
            @Param("envId") Long envId,
            @Param("status") UserStatus status,
            @Param("sex") Sex sex);


    @Query("""
            SELECT a FROM Assignment a
            WHERE a.env = :env
               AND a.envId = :envId
               AND (:userIds IS NULL OR a.user.id NOT IN :userIds)
               AND a.user.status = :status
            """)
    List<Assignment> findAssignmentsListExcludingUsersByIds(
            @Param("env") Env env,
            @Param("envId") Long envId,
            @Param("userIds") List<Long> userIds,
            @Param("status") UserStatus status);
}
