package com.milosz000.controller;

import com.milosz000.dto.AuthenticationResponseDto;
import com.milosz000.dto.RegisterRequestDto;
import com.milosz000.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(@Valid @RequestBody RegisterRequestDto registerRequestDto){

        userService.register(registerRequestDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
