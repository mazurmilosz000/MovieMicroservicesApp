package com.milosz000.service;

import com.milosz000.dto.AuthenticationRequestDto;
import com.milosz000.dto.AuthenticationResponseDto;
import com.milosz000.dto.RegisterRequestDto;

public interface UserService {
    AuthenticationResponseDto register(RegisterRequestDto registerRequestDto);

    AuthenticationResponseDto login(AuthenticationRequestDto request);

    void enableUserAccount(String email);

    String confirmAccount(String token);
}
