package com.sashaprylutskyy.squidgamems.service;

import com.sashaprylutskyy.squidgamems.model.Lobby;
import com.sashaprylutskyy.squidgamems.model.RefCode;
import com.sashaprylutskyy.squidgamems.model.Role;
import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.dto.refCode.RefCodeSummaryDTO;
import com.sashaprylutskyy.squidgamems.model.dto.user.*;
import com.sashaprylutskyy.squidgamems.model.enums.UserStatus;
import com.sashaprylutskyy.squidgamems.model.mapper.RefCodeMapper;
import com.sashaprylutskyy.squidgamems.model.mapper.UserMapper;
import com.sashaprylutskyy.squidgamems.repository.RoleRepository;
import com.sashaprylutskyy.squidgamems.repository.UserRepository;
import com.sashaprylutskyy.squidgamems.security.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;
    private final RoleService roleService;
    private final UserMapper userMapper;
    private final LobbyService lobbyService;
    private final RoleRepository roleRepo;
    private final RefCodeService refCodeService;
    private final RefCodeMapper refCodeMapper;
    private final RecruitmentLogService recruitmentLogService;


    public UserService(UserRepository userRepo, JwtService jwtService,
                       PasswordEncoder encoder, RoleService roleService,
                       UserMapper userMapper, LobbyService lobbyService,
                       RoleRepository roleRepo, RefCodeService refCodeService,
                       RefCodeMapper refCodeMapper, RecruitmentLogService recruitmentLogService) {
        this.userRepo = userRepo;
        this.jwtService = jwtService;
        this.encoder = encoder;
        this.roleService = roleService;
        this.userMapper = userMapper;
        this.lobbyService = lobbyService;
        this.roleRepo = roleRepo;
        this.refCodeService = refCodeService;
        this.refCodeMapper = refCodeMapper;
        this.recruitmentLogService = recruitmentLogService;
    }

    public User getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() ||
                !(authentication.getPrincipal() instanceof UserDetails)) {
            throw new NullPointerException("User is not authenticated.");
        }

        return (User) authentication.getPrincipal();
    }

    public User getUserByEmail(String email) {
        return userRepo.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User %s not found".formatted(email)));
    }

    public User getUserById(Long id) {
        return userRepo.findUserById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User No.%d not found".formatted(id)));
    }

    @Transactional
    public UserResponseDTO registerHOSTorVIP(UserRequestDTO dto) {
        Role role = roleService.getRoleById(dto.getRoleId());

        if (!role.toString().equals("HOST") && !role.toString().equals("VIP")) {
            throw new RuntimeException("You're able to register an account with either Host or VIP role.");
        }

        User user = createUserFromData(dto);
        lobbyService.assignUserToLobby(user, user.getId());
        return userMapper.toResponseDTO(user);
    }

    @Transactional
    public UserSummaryDTO registerPlayer(UserRequestPlayerDTO dto) {
        RefCode refCode = refCodeService.getRefCode(dto.getRefCode());
        User salesman = refCode.getUser();

        Lobby lobby = lobbyService.getLobbyByUserId(salesman.getId());
        Long lobbyId = lobby.getLobbyId();

        UserRequestDTO playerDTO = userMapper.toUserRequestDTO(dto);
        Long playerRoleId = roleService.getRoleIdByTitle("PLAYER");
        playerDTO.setRoleId(playerRoleId);

        User player = createUserFromData(playerDTO);

        recruitmentLogService.addLog(player, refCode);
        lobbyService.assignUserToLobby(player, lobbyId);

        return userMapper.toSummaryDTO(player);
    }

    @Transactional
    public User createUserFromData(UserRequestDTO dto) {
        try {
            Long currentTime = System.currentTimeMillis();

            User user = userMapper.toEntity(dto);
            user.setPassword(encoder.encode(user.getPassword()));
            user.setStatus(UserStatus.ALIVE);
            user.setCreatedAt(currentTime);
            user.setUpdatedAt(currentTime);
            user.setRole(
                    roleRepo.getReferenceById(dto.getRoleId())
            );

            return userRepo.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateKeyException("Email is already taken.");
        }
    }

    public RefCodeSummaryDTO getMyRefCode() {
        User principal = getPrincipal();
        RefCode refCode = refCodeService.getRefCode(principal);
        return refCodeMapper.toSummaryDTO(refCode);
    }

    public String login(LoginRequestDTO dto) {
        User user = getUserByEmail(dto.getEmail());
        if (encoder.matches(dto.getPassword(), user.getPassword())) {
            return jwtService.generateToken(user);
        }
        throw new RuntimeException("Invalid credentials");
    }
}
