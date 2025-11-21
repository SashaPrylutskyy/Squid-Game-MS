package com.sashaprylutskyy.squidgamems.repository;

import com.sashaprylutskyy.squidgamems.model.RecruitmentLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecruitmentLogRepo extends JpaRepository<RecruitmentLog, Long> {

    @Query("""
            SELECT new com.sashaprylutskyy.squidgamems.model.dto.reports.RecruitmentStatsDTO(
                rl.refCode.user.id,
                rl.refCode.user.email,
                rl.refCode.refCode,
                CAST(COUNT(rl) as int),
                CAST(SUM(CASE WHEN rl.player.status = 'ALIVE' THEN 1 ELSE 0 END) as int)
            )
            FROM RecruitmentLog rl
            GROUP BY rl.refCode.user.id, rl.refCode.user.email, rl.refCode.refCode
            ORDER BY COUNT(rl) DESC
            """)
    List<com.sashaprylutskyy.squidgamems.model.dto.reports.RecruitmentStatsDTO> getRecruitmentStats();
}
