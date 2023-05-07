package com.milosz000.controller;

import com.milosz000.dto.*;
import com.milosz000.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService userService;

    @Operation(
            description = "Endpoint for create new user",
            summary = "Register user endpoint",
            responses = {
                    @ApiResponse(
                            description = "Account created",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Bad credentials",
                            responseCode = "400"
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequestDto registerRequestDto){

        userService.register(registerRequestDto);
        return ResponseEntity.ok("Account created! Now check your email");
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

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        userService.refreshToken(request, response);
    }

}
