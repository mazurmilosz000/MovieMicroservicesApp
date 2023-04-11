package com.milosz000.controller;

import com.milosz000.dto.*;
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
        // TODO: change it (I dont want to return token, bcs account is not enabled)
        return ResponseEntity.ok(userService.register(registerRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(@Valid @RequestBody AuthenticationRequestDto request) {
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

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody EmailDto emailRequest) {

        userService.sendResetPasswordLink(emailRequest.getEmail());

        /*
            Even if the email does not exist, It will show a message that says the email has been sent successfully.
            That way I don't give attackers any indication that they should try a different email address.
         */
        return "email has been sent successfully";
    }

    @PostMapping("/new-password")
    public String resetPasswordCredentials(@RequestParam String token,
                                           @Valid @RequestBody NewPasswordRequestDto newPasswordDto) {
        return userService.changePassword(token, newPasswordDto);
    }

}
