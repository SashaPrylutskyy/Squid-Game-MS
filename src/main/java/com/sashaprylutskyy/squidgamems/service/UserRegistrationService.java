package com.sashaprylutskyy.squidgamems.service;

import com.sashaprylutskyy.squidgamems.model.Assignment;
import com.sashaprylutskyy.squidgamems.model.RefCode;
import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.dto.user.UserRequestDTO;
import com.sashaprylutskyy.squidgamems.model.dto.user.UserRequestPlayerDTO;
import com.sashaprylutskyy.squidgamems.model.dto.user.UserResponseDTO;
import com.sashaprylutskyy.squidgamems.model.dto.user.UserSummaryDTO;
import com.sashaprylutskyy.squidgamems.model.enums.Role;
import com.sashaprylutskyy.squidgamems.model.mapper.UserMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {

    private final UserService userService;
    private final AssignmentService assignmentService;
    private final RefCodeService refCodeService;
    private final RecruitmentLogService recruitmentLogService;
    private final UserMapper userMapper;

    public UserRegistrationService(UserService userService, AssignmentService assignmentService,
                                   RefCodeService refCodeService,
                                   RecruitmentLogService recruitmentLogService, UserMapper userMapper) {
        this.userService = userService;
        this.assignmentService = assignmentService;
        this.refCodeService = refCodeService;
        this.recruitmentLogService = recruitmentLogService;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserResponseDTO registerHOSTorVIP(UserRequestDTO dto) {
        if (dto.getRole() != Role.HOST && dto.getRole() != Role.VIP) {
            throw new RuntimeException("You're able to register an account with either HOST or VIP role.");
        }
        User user = userService.createUserFromData(dto);
        if (user.getRole() == Role.HOST) {
            assignmentService.assignUserToLobby(user, user.getId());
        }
        return userMapper.toResponseDTO(user);
    }

    @Transactional
    public UserSummaryDTO registerPlayer(UserRequestPlayerDTO dto) {
        RefCode refCode = refCodeService.getRefCode(dto.getRefCode());
        User salesman = refCode.getUser();

        Assignment salesmanAssignment = assignmentService.getAssignment_Lobby(salesman);
        Long lobbyId = salesmanAssignment.getEnvId();

        UserRequestDTO playerDTO = userMapper.toUserRequestDTO(dto);
        playerDTO.setRole(Role.PLAYER);

        User player = userService.createUserFromData(playerDTO);
        recruitmentLogService.addLog(player, refCode);
        assignmentService.assignUserToLobby(player, lobbyId);

        return userMapper.toSummaryDTO(player);
    }
}

