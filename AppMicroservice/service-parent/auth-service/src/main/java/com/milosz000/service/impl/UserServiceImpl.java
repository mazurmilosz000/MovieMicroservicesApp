package com.milosz000.service.impl;

import com.milosz000.dto.RegisterRequestDto;
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

    @Override
    public String register(RegisterRequestDto registerRequestDto) {
        Optional<User> optionalEmail = userRepository.findByEmail(registerRequestDto.getEmail());
        Optional<User> optionalUsername = userRepository.findByUsername(registerRequestDto.getUsername());

        if(optionalUsername.isPresent()) return "username exists";

        if(optionalEmail.isPresent()) return "email exists";

//        if(!validatePassword(registerRequestDto.getPassword(), registerRequestDto.getPasswordConfirmation())) {
//            return "different password";
//        }

        // TODO: make password validator/create password regex
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

        return "user added";
    }

//    private boolean validatePassword(String password, String passwordRepeated) {
//        return password.equals(passwordRepeated);
//    }
}
