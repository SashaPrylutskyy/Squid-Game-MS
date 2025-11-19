package com.sashaprylutskyy.squidgamems.repository;

import com.sashaprylutskyy.squidgamems.model.RoundResult;
import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoundResultRepo extends JpaRepository<RoundResult, Long> {

        @Query("""
                        SELECT rr FROM RoundResult  rr
                        WHERE rr.round.id = :roundId
                        AND rr.user.id IN :userIds
                        AND rr.confirmedAt IS NULL
                        AND rr.status != :userStatus
                        """)
        List<RoundResult> findAllBy(
                        @Param("roundId") Long roundId,
                        @Param("userIds") List<Long> userIds,
                        @Param("userStatus") UserStatus userStatus);

        List<RoundResult> findAllByRoundId(Long roundId);

        List<RoundResult> findAllByRoundIdAndStatus(Long roundId, UserStatus status);

        java.util.Optional<RoundResult> findTopByUserOrderByIdDesc(User user);
}
