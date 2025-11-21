package com.sashaprylutskyy.squidgamems.controller;

import com.sashaprylutskyy.squidgamems.model.dto.reports.*;
import com.sashaprylutskyy.squidgamems.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@Secured({ "ROLE_HOST", "ROLE_FRONTMAN" })
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/players/statistics")
    public ResponseEntity<List<PlayerStatsDTO>> getPlayerStatistics() {
        return ResponseEntity.ok(reportService.getPlayerStatistics());
    }

    @GetMapping("/competitions/{competitionId}")
    public ResponseEntity<CompetitionStatsDTO> getCompetitionDetails(@PathVariable Long competitionId) {
        return ResponseEntity.ok(reportService.getCompetitionDetails(competitionId));
    }

    @GetMapping("/rounds/top-hardest")
    public ResponseEntity<List<RoundStatsDTO>> getTopHardestRounds() {
        return ResponseEntity.ok(reportService.getTopHardestRounds());
    }

    @GetMapping("/voting-analytics")
    public ResponseEntity<VotingStatsDTO> getVotingAnalysis() {
        return ResponseEntity.ok(reportService.getVotingAnalysis());
    }

    @GetMapping("/financial/vip-rating")
    public ResponseEntity<List<VipStatsDTO>> getVipRating() {
        return ResponseEntity.ok(reportService.getVipRating());
    }

    @GetMapping("/staff/performance")
    public ResponseEntity<List<StaffStatsDTO>> getStaffEfficiency() {
        return ResponseEntity.ok(reportService.getStaffEfficiency());
    }

    @GetMapping("/recruitment/stats")
    public ResponseEntity<List<RecruitmentStatsDTO>> getRecruitmentStats() {
        return ResponseEntity.ok(reportService.getRecruitmentStats());
    }
}
