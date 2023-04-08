package com.milosz000.controller;

import com.milosz000.dto.AuthenticationRequestDto;
import com.milosz000.dto.AuthenticationResponseDto;
import com.milosz000.dto.RegisterRequestDto;
import com.milosz000.service.ConfirmationTokenService;
import com.milosz000.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(@Valid @RequestBody RegisterRequestDto registerRequestDto){

        // return status code 200 and token
        return ResponseEntity.ok(userService.register(registerRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody AuthenticationRequestDto request) {
        return ResponseEntity.ok(userService.login(request));
    }

    // TODO : make it better
    @GetMapping("/confirm")
    public String confirmAccount(@RequestParam String token) {
        if (userService.confirmAccount(token).equals("confirmed")) {
            return "account confirmed";
        }
        return "not confirmed";
    }

}
