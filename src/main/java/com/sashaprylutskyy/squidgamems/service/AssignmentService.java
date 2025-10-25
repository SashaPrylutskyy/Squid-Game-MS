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

    public Assignment getByCompetitionIdAndPlayerId(Long competitionId, Long playerId) {
        return assignmentRepo.findByCompetitionIdAndPlayerId(competitionId, playerId);
    }

    @Transactional
    public AssignmentResponseDTO assignPlayersToCompetition(AssignmentRequestDTO dto) {
        User principal = userService.getPrincipal();
        Long competitionId = dto.getCompetitionId(); //todo переконатися, що competition існує
        Long now = System.currentTimeMillis();

        List<Assignment> assignmentsToSave = new ArrayList<>();
        List<User> playerEntities = new ArrayList<>();

        for (Long playerId : dto.getPlayerIds()) {
            Assignment assignment = getByCompetitionIdAndPlayerId(competitionId, playerId);
            if (assignment != null) {
                continue;
            }

            User player = userService.getUserById(playerId);
            playerEntities.add(player);

            Assignment newAssignment = new Assignment(
                    competitionId,
                    player,
                    principal,
                    now
            );
            assignmentsToSave.add(newAssignment);
        }

        assignmentRepo.saveAll(assignmentsToSave);
        return toResponseDTO(principal, competitionId, now, playerEntities);
    }

    public AssignmentResponseDTO removePlayersFromCompetition(AssignmentRequestDTO dto) {
        User principal = userService.getPrincipal();
        Long competitionId = dto.getCompetitionId(); //todo переконатися, що competition існує
        Long now = System.currentTimeMillis();

        List<Assignment> assignmentsToDelete = new ArrayList<>();
        List<User> playerEntities = new ArrayList<>();

        for (Long playerId : dto.getPlayerIds()) {
            Assignment assignment = getByCompetitionIdAndPlayerId(competitionId, playerId);
            if (assignment != null) {
                assignmentsToDelete.add(assignment);
                playerEntities.add(assignment.getPlayer());
            }
        }
        assignmentRepo.deleteAll(assignmentsToDelete);

        return toResponseDTO(principal, competitionId, now, playerEntities);
    }

    private AssignmentResponseDTO toResponseDTO(User principal, Long competitionId, Long currentTime, List<User> playerEntities) {
        AssignmentResponseDTO response = new AssignmentResponseDTO();
        response.setCompetitionId(competitionId);
        response.setProcessedAt(currentTime);

        response.setProcessedBy(userMapper.toSummaryDTO(principal));
        List<UserSummaryDTO> playerDTOs = playerEntities.stream()
                .map(userMapper::toSummaryDTO)
                .toList();

        response.setPlayers(playerDTOs);

        return response;
    }

}
