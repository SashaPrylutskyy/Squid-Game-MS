package com.sashaprylutskyy.squidgamems.controller;

import com.sashaprylutskyy.squidgamems.model.dto.user.UserRequestDTO;
import com.sashaprylutskyy.squidgamems.model.dto.user.UserResponseDTO;
import com.sashaprylutskyy.squidgamems.model.interfaceGroup.OnCreate;
import com.sashaprylutskyy.squidgamems.model.interfaceGroup.OnLogin;
import com.sashaprylutskyy.squidgamems.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> createUser(@Validated(OnCreate.class) @RequestBody UserRequestDTO dto) {
        UserResponseDTO userResponse = userService.registerUser(dto);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Validated(OnLogin.class) @RequestBody UserRequestDTO dto) {
        String jwt = userService.login(dto);
        return ResponseEntity.ok(jwt);
    }
}
