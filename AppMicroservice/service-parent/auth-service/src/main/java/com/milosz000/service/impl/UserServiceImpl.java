package com.milosz000.service.impl;

import com.milosz000.config.JwtService;
import com.milosz000.dto.AuthenticationResponseDto;
import com.milosz000.dto.RegisterRequestDto;
import com.milosz000.exception.ApiRequestException;
import com.milosz000.model.User;
import com.milosz000.model.enums.Role;
import com.milosz000.repository.UserRepository;
import com.milosz000.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthenticationResponseDto register(RegisterRequestDto registerRequestDto) {
        Optional<User> optionalEmail = userRepository.findByEmail(registerRequestDto.getEmail());
        Optional<User> optionalUsername = userRepository.findByUsername(registerRequestDto.getUsername());

        if(optionalUsername.isPresent()) throw new ApiRequestException("Username already in use");

        if(optionalEmail.isPresent()) throw new ApiRequestException("Email already in use");


        User user = User.builder()
                .username(registerRequestDto.getUsername())
                .email(registerRequestDto.getEmail())
                .firstName(registerRequestDto.getFirstname())
                .lastName(registerRequestDto.getLastname())
                .password(passwordEncoder.encode(registerRequestDto.getPassword()))
                .isEnabled(false)
                .role(Role.USER)
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user);

        return AuthenticationResponseDto.builder().token(token).build();



    }
}
