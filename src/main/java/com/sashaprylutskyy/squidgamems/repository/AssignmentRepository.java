package com.sashaprylutskyy.squidgamems.repository;

import com.sashaprylutskyy.squidgamems.model.Assignment;
import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.enums.Env;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    @Query("""
            SELECT a FROM Assignment a
            WHERE a.env =:env 
            AND a.envId =:envId 
            AND a.user.id =:userId
            """)
    Assignment findByEnvAndEnvIdAndUserId(
            @Param("env") Env env,
            @Param("envId") Long envId,
            @Param("userId") Long userId
    );

    Optional<Assignment> findAssignmentByEnvAndUser(Env env, User user);
}
