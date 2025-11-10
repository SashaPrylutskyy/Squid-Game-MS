package com.sashaprylutskyy.squidgamems.repository;

import com.sashaprylutskyy.squidgamems.model.RoundResult;
import com.sashaprylutskyy.squidgamems.model.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoundResultRepo extends JpaRepository<RoundResult, Long> {

    @Query("select rr from RoundResult rr where rr.round.id = :roundId and rr.user.id in :userIds and rr.confirmedAt is null")
    List<RoundResult> findAllBy(
            @Param("roundId") Long roundId, @Param("userIds") List<Long> userIds
    );

    List<RoundResult> findAllByRoundId(Long roundId);

    List<RoundResult> findAllByRoundIdAndStatus(Long roundId, UserStatus status);
}
