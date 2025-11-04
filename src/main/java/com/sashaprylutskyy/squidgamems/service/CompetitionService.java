package com.sashaprylutskyy.squidgamems.service;

import com.sashaprylutskyy.squidgamems.model.Assignment;
import com.sashaprylutskyy.squidgamems.model.Competition;
import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.dto.competition.CompetitionResponseDTO;
import com.sashaprylutskyy.squidgamems.model.mapper.CompetitionMapper;
import com.sashaprylutskyy.squidgamems.repository.CompetitionRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CompetitionService {

    private final CompetitionRepo competitionRepo;
    private final AssignmentService assignmentService;
    private final CompetitionMapper competitionMapper;


    public CompetitionService(CompetitionRepo competitionRepo, AssignmentService assignmentService,
                              CompetitionMapper competitionMapper) {
        this.competitionRepo = competitionRepo;
        this.assignmentService = assignmentService;
        this.competitionMapper = competitionMapper;
    }

    public Competition getById(Long id) {
        return competitionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Competition No.%d not found".formatted(id)));
    }

    @Transactional
    public CompetitionResponseDTO create(String title, User principal) {
        Assignment lobby = assignmentService.getAssignment_Lobby_byUser(principal);
        Long now = System.currentTimeMillis();

        Competition competition = new Competition(title, lobby.getEnvId(), principal, now);
        competition = competitionRepo.save(competition);

        return competitionMapper.toResponseDTO(competition);
    }
}
