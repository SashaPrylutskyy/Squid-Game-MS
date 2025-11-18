package com.sashaprylutskyy.squidgamems.model.dto.user;

import com.sashaprylutskyy.squidgamems.model.dto.round.CurrentRoundDTO;

import java.util.List;

public class WorkerAssignmentResponseDTO {
    private CurrentRoundDTO currentRound;
    private List<PlayerReportDTO> playersToReport;

    public WorkerAssignmentResponseDTO(CurrentRoundDTO currentRound,
                                       List<PlayerReportDTO> playersToReport) {
        this.currentRound = currentRound;
        this.playersToReport = playersToReport;
    }

    public WorkerAssignmentResponseDTO() {

    }

    public CurrentRoundDTO getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(CurrentRoundDTO currentRound) {
        this.currentRound = currentRound;
    }

    public List<PlayerReportDTO> getPlayersToReport() {
        return playersToReport;
    }

    public void setPlayersToReport(List<PlayerReportDTO> playersToReport) {
        this.playersToReport = playersToReport;
    }
}
