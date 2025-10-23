package com.sashaprylutskyy.squidgamems.controller;

import com.sashaprylutskyy.squidgamems.model.dto.UserRequestDTO;
import com.sashaprylutskyy.squidgamems.model.dto.UserResponseDTO;
import com.sashaprylutskyy.squidgamems.model.interfaceGroup.OnCreate;
import com.sashaprylutskyy.squidgamems.model.interfaceGroup.OnLogin;
import com.sashaprylutskyy.squidgamems.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<UserResponseDTO> createUser(@Validated(OnCreate.class) @RequestBody UserRequestDTO dto) {
        UserResponseDTO userResponse = userService.registerUser(dto);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<String> login(@Validated(OnLogin.class) @RequestBody UserRequestDTO dto) {
        String jwt = userService.login(dto);
        return ResponseEntity.ok(jwt);
    }
}
