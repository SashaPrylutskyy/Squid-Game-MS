package com.sashaprylutskyy.squidgamems.service;

import com.sashaprylutskyy.squidgamems.model.Assignment;
import com.sashaprylutskyy.squidgamems.model.Competition;
import com.sashaprylutskyy.squidgamems.model.Round;
import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.dto.competition.CompetitionResponseDTO;
import com.sashaprylutskyy.squidgamems.model.enums.CompetitionRoundStatus;
import com.sashaprylutskyy.squidgamems.model.enums.Env;
import com.sashaprylutskyy.squidgamems.model.mapper.CompetitionMapper;
import com.sashaprylutskyy.squidgamems.repository.CompetitionRepo;
import com.sashaprylutskyy.squidgamems.repository.RoundRepo;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompetitionService {

    @Value("${min-players}")
    private int minPlayers;

    private final CompetitionRepo competitionRepo;
    private final AssignmentService assignmentService;
    private final CompetitionMapper competitionMapper;
    private final RoundRepo roundRepo;


    public CompetitionService(CompetitionRepo competitionRepo, AssignmentService assignmentService,
                              CompetitionMapper competitionMapper, RoundRepo roundRepo) {
        this.competitionRepo = competitionRepo;
        this.assignmentService = assignmentService;
        this.competitionMapper = competitionMapper;
        this.roundRepo = roundRepo;
    }

    public Competition getById(Long id) {
        return competitionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Competition No.%d not found".formatted(id)));
    }

    @Transactional
    public CompetitionResponseDTO create(String title, User principal) {
        Assignment lobby = assignmentService.getAssignment_Lobby(principal);
        Long now = System.currentTimeMillis();

        Competition competition = new Competition(title, lobby.getEnvId(), principal, now);
        competition = competitionRepo.save(competition);

        return competitionMapper.toResponseDTO(competition);
    }

    public Round getRoundById(Long roundId) {
        return roundRepo.findById(roundId)
                .orElseThrow(() -> new NoResultException(
                        "Round No.%d is not found.".formatted(roundId))
                );
    }

    @Transactional
    public CompetitionResponseDTO startCompetition(Long competitionId) {
        Competition competition = getById(competitionId);

        List<Assignment> assignments = assignmentService
                .getListOfAssignments(Env.COMPETITION, competition.getId());
        if (assignments.size() < this.minPlayers) {
            throw new RuntimeException("Cannot start a competition: %d/%d players to start."
                    .formatted(assignments.size(), minPlayers));
        }
        competition.setStatus(CompetitionRoundStatus.ACTIVE);
        competition = competitionRepo.save(competition);
        return competitionMapper.toResponseDTO(competition);
    }

    @Transactional
    public void endCompetition(Long roundId, int continueGame, int quitGame, int remaining) {
        if (remaining == 0 && (quitGame > continueGame)) {
            Competition competition = getRoundById(roundId).getCompetition();
            competition.setStatus(CompetitionRoundStatus.COMPLETED);
            competitionRepo.save(competition);
            //todo прописати виплату грошової винагороди вижившим гравцям
        }
    }
}
