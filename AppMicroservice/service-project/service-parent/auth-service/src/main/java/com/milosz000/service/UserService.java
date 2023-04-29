package com.milosz000.service;

import com.milosz000.dto.*;

public interface UserService {
    AuthenticationResponseDto register(RegisterRequestDto registerRequestDto);

    AuthenticationResponseDto login(AuthenticationRequestDto request);

    void enableUserAccount(String email);

    String confirmAccount(String token);

    void sendResetPasswordLink(String email);

    String changePassword(String resetPasswordToken, NewPasswordRequestDto newPasswordDto);
}
