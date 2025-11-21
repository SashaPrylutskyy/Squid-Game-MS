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
                        SELECT rr FROM RoundResult rr
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

        @Query("""
                        SELECT rr FROM RoundResult rr
                        JOIN rr.user u
                        WHERE rr.round.id = :roundId
                        AND rr.status = :status
                        AND u.sex = :sex
                        """)
        List<RoundResult> findAllByRoundIdAndStatusAndUserSex(
                        @Param("roundId") Long roundId,
                        @Param("status") UserStatus status,
                        @Param("sex") com.sashaprylutskyy.squidgamems.model.enums.Sex sex);

        @Query("""
                        SELECT new com.sashaprylutskyy.squidgamems.model.dto.reports.StaffStatsDTO(
                            rr.reportedBy.id,
                            rr.reportedBy.email,
                            CAST(COUNT(rr) as int),
                            AVG(CASE WHEN rr.confirmedAt IS NOT NULL THEN (rr.confirmedAt - rr.reportedAt) ELSE 0 END),
                            0
                        )
                        FROM RoundResult rr
                        WHERE rr.reportedBy IS NOT NULL
                        GROUP BY rr.reportedBy.id, rr.reportedBy.email
                        """)
        List<com.sashaprylutskyy.squidgamems.model.dto.reports.StaffStatsDTO> getStaffStats();
}
