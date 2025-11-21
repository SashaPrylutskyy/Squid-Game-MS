package com.sashaprylutskyy.squidgamems.controller;

import com.sashaprylutskyy.squidgamems.model.dto.reports.*;
import com.sashaprylutskyy.squidgamems.service.ReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReportController.class)
@AutoConfigureMockMvc(addFilters = false) // Disable security filters for simplicity in this unit test
public class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService reportService;

    @Test
    @WithMockUser(roles = "HOST")
    public void getPlayerStatistics_ShouldReturnOk() throws Exception {
        given(reportService.getPlayerStatistics()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/reports/players/statistics")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "HOST")
    public void getCompetitionDetails_ShouldReturnOk() throws Exception {
        Long competitionId = 1L;
        given(reportService.getCompetitionDetails(competitionId))
                .willReturn(new CompetitionStatsDTO(competitionId, 0L, 0L, Collections.emptyList()));

        mockMvc.perform(get("/reports/competitions/{competitionId}", competitionId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "HOST")
    public void getTopHardestRounds_ShouldReturnOk() throws Exception {
        given(reportService.getTopHardestRounds()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/reports/rounds/top-hardest")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "HOST")
    public void getVotingAnalysis_ShouldReturnOk() throws Exception {
        given(reportService.getVotingAnalysis()).willReturn(new VotingStatsDTO(0L, 0.0, 0.0, Collections.emptyMap()));

        mockMvc.perform(get("/reports/voting-analytics")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "HOST")
    public void getVipRating_ShouldReturnOk() throws Exception {
        given(reportService.getVipRating()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/reports/financial/vip-rating")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "HOST")
    public void getStaffEfficiency_ShouldReturnOk() throws Exception {
        given(reportService.getStaffEfficiency()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/reports/staff/performance")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "HOST")
    public void getRecruitmentStats_ShouldReturnOk() throws Exception {
        given(reportService.getRecruitmentStats()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/reports/recruitment/stats")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
