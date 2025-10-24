package com.sashaprylutskyy.squidgamems.service;

import com.sashaprylutskyy.squidgamems.model.Assignment;
import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.dto.assignment.AssignmentRequestDTO;
import com.sashaprylutskyy.squidgamems.model.dto.assignment.AssignmentResponseDTO;
import com.sashaprylutskyy.squidgamems.model.enums.EnvType;
import com.sashaprylutskyy.squidgamems.model.mapper.AssignmentMapper;
import com.sashaprylutskyy.squidgamems.model.mapper.UserMapper;
import com.sashaprylutskyy.squidgamems.repository.AssignmentRepository;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Service
public class AssignmentService {

    private final AssignmentMapper assignmentMapper;
    private final AssignmentRepository assignmentRepository;
    private final UserService userService;


    public AssignmentService(AssignmentMapper assignmentMapper, AssignmentRepository assignmentRepository,
                             UserService userService) {
        this.assignmentMapper = assignmentMapper;
        this.assignmentRepository = assignmentRepository;
        this.userService = userService;
    }

     public AssignmentResponseDTO assign() {
       /* User user = userService.getUserById(dto.getUserId());
        User principal = userService.getPrincipal();

        Assignment assignment = new Assignment(
                dto.getEnvType(),
                principal.getId(),
                user,
                principal,
                System.currentTimeMillis()
        );

        assignment = assignmentRepository.save(assignment);
        return assignmentMapper.toResponseDTO(assignment);*/
         return null;
    }

    //The goal of this method is to assign a player to a competition
    public AssignmentResponseDTO assign(AssignmentRequestDTO dto) {
        User player = userService.getUserById(dto.getUserId());
        if (!player.getRole().toString().equals("PLAYER")) {
            throw new RuntimeException("Only a player can be assigned to a competition");
        }
        User principal = userService.getPrincipal();

        Assignment assignment = new Assignment(
                EnvType.COMPETITION,
                dto.getEnvId(), //todo потрібно переконатися, що зазначене competition існує
                player,
                principal,
                System.currentTimeMillis()
        );

        assignment = assignmentRepository.save(assignment);
        return assignmentMapper.toResponseDTO(assignment);
    }
}
