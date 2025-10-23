package com.sashaprylutskyy.squidgamems.service;

import com.sashaprylutskyy.squidgamems.model.Role;
import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.dto.UserRequestDTO;
import com.sashaprylutskyy.squidgamems.model.dto.UserResponseDTO;
import com.sashaprylutskyy.squidgamems.model.mapper.UserMapper;
import com.sashaprylutskyy.squidgamems.repository.UserRepository;
import com.sashaprylutskyy.squidgamems.security.JwtService;
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


    public UserService(UserRepository userRepo, JwtService jwtService, PasswordEncoder encoder, RoleService roleService) {
        this.userRepo = userRepo;
        this.jwtService = jwtService;
        this.encoder = encoder;
        this.roleService = roleService;
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

    //todo
    public UserResponseDTO registerUser(UserRequestDTO dto, String token) {
        return null;
    }


    public UserResponseDTO registerUser(UserRequestDTO dto) {
        Role role = roleService.getRoleById(dto.getRoleId());

        if (!role.toString().equals("Host") && !role.toString().equals("VIP")) {
            throw new RuntimeException("You're able to register account with either Host or VIP role.");
        }
        try {
            Long currentTime = System.currentTimeMillis();

            User user = UserMapper.toEntity(dto);
            user.setPassword(user.getPassword());
            user.setCreatedAt(currentTime);
            user.setUpdatedAt(currentTime);
            userRepo.save(user);

            return UserMapper.toResponse(user);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateKeyException("Email is already taken.");
        }
    }

    public String login(UserRequestDTO dto) {
        User user = getUserByEmail(dto.getEmail());
        if (encoder.matches(dto.getPassword(), user.getPassword())) {
            return jwtService.generateToken(user);
        }
        throw new RuntimeException("Invalid credentials");
    }
}
