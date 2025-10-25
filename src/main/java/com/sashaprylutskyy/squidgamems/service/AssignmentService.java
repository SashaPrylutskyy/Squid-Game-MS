package com.sashaprylutskyy.squidgamems.service;

import com.sashaprylutskyy.squidgamems.model.Assignment;
import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.dto.assignment.AssignmentRequestDTO;
import com.sashaprylutskyy.squidgamems.model.dto.assignment.AssignmentResponseDTO;
import com.sashaprylutskyy.squidgamems.model.dto.user.UserSummaryDTO;
import com.sashaprylutskyy.squidgamems.model.mapper.UserMapper;
import com.sashaprylutskyy.squidgamems.repository.AssignmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepo;
    private final UserService userService;
    private final UserMapper userMapper;


    public AssignmentService(AssignmentRepository assignmentRepo, UserService userService,
                             UserMapper userMapper) {
        this.assignmentRepo = assignmentRepo;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    //The goal of this method is to assign players to a competition
    @Transactional
    public AssignmentResponseDTO assignPlayersToCompetition(AssignmentRequestDTO dto) {
        User principal = userService.getPrincipal();
        Long competitionId = dto.getCompetitionId(); //todo переконатися, що competition існує
        Long now = System.currentTimeMillis();

        List<Assignment> assignmentsToSave = new ArrayList<>();
        List<User> playerEntities = new ArrayList<>();

        for (Long playerId : dto.getPlayerIds()) {
            User player = userService.getUserById(playerId);
            playerEntities.add(player);

            Assignment assignment = new Assignment(
                    competitionId,
                    player,
                    principal,
                    now
            );
            assignmentsToSave.add(assignment);
        }

        assignmentRepo.saveAll(assignmentsToSave);
        AssignmentResponseDTO response = new AssignmentResponseDTO();
        response.setCompetitionId(competitionId);
        response.setAssignedAt(now);

        response.setAssignedBy(userMapper.toSummaryDTO(principal));
        List<UserSummaryDTO> playerDTOs = playerEntities.stream()
                .map(userMapper::toSummaryDTO)
                .toList();

        response.setPlayers(playerDTOs);
        return response;
    }
}
