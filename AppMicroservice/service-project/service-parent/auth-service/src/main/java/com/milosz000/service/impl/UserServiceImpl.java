package com.milosz000.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.Hashing;
import com.milosz000.amqp.AMQPTokenEmail;
import com.milosz000.config.JwtService;
import com.milosz000.constants.Naming;
import com.milosz000.dto.AuthenticationRequestDto;
import com.milosz000.dto.AuthenticationResponseDto;
import com.milosz000.dto.NewPasswordRequestDto;
import com.milosz000.dto.RegisterRequestDto;
import com.milosz000.exception.ApiRequestException;
import com.milosz000.exception.ExpiredTokenException;
import com.milosz000.exception.InvalidTokenException;
import com.milosz000.model.ConfirmationToken;
import com.milosz000.model.PasswordResetToken;
import com.milosz000.model.User;
import com.milosz000.repository.ConfirmationTokenRepository;
import com.milosz000.repository.ResetPasswordTokenRepository;
import com.milosz000.repository.UserRepository;
import com.milosz000.service.ConfirmationTokenService;
import com.milosz000.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final ConfirmationTokenService confirmationTokenService;

    private final AuthenticationManager authenticationManager;

    private final ConfirmationTokenRepository confirmationTokenRepository;

    private final ResetPasswordTokenRepository resetPasswordTokenRepository;

    private final RabbitTemplate rabbitTemplate;

    @Value(value = "${TOKEN_EXP_TIME}")
    private int TOKEN_EXP_TIME;

    @Transactional
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
                .role(Naming.Role.USER)
                .build();

        userRepository.save(user);

        // generate authentication token
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(TOKEN_EXP_TIME),
                user
        );

        // send confirmation email
        sendEmail(
                registerRequestDto.getEmail(),
                registerRequestDto.getFirstname(),
                token,
                Naming.AMQP.CONFIRMATION_TOKEN_ROUTING_KEY
                );
        confirmationTokenService.saveConfirmationToken(confirmationToken);


        return AuthenticationResponseDto.builder()
                .accessToken(token)
                .build();


    }

    @Override
    public AuthenticationResponseDto login(AuthenticationRequestDto request) {

        var user =
                userRepository.findByUsername(request.getUsername()).orElseThrow(
                        () -> new UsernameNotFoundException("Username or password are incorrect")
                );

        if (!user.isEnabled()) throw new ApiRequestException("Account not enabled");


        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));



        var token = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return AuthenticationResponseDto.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void enableUserAccount(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("Cannot find user with that email"));

        user.setEnabled(true);
        userRepository.save(user);
    }

    @Transactional
    @Override
    public String confirmAccount(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository
                .findByToken(token)
                .orElseThrow(() -> new IllegalStateException("token not found"));

        // check if token is not already confirmed
        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime currentTime = LocalDateTime.now();

        // check if token is not expired
        if (confirmationToken.getExpiresAt().isBefore(currentTime)) {
            throw new IllegalStateException("token is expired");
        }

        confirmationToken.setConfirmedAt(currentTime);

        confirmationTokenRepository.save(confirmationToken);
        enableUserAccount(confirmationToken.getUser().getEmail());

        return "confirmed";

    }

    @Override
    public void sendResetPasswordLink(String email) {
        // TODO: when I am typing wrong email, I am receiving 403 instead of 200, (need to fix)
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Cannot find user with that email"));

        // create reset password token
        String token = UUID.randomUUID().toString();
        String hashedToken = Hashing.sha256().hashString(token, StandardCharsets.UTF_8).toString();

        LocalDateTime currentTime = LocalDateTime.now();

        PasswordResetToken passwordResetToken = new PasswordResetToken(
                hashedToken,
                currentTime,
                currentTime.plusMinutes(TOKEN_EXP_TIME),
                user
        );

        // send email with reset password link
        sendEmail(
                user.getEmail(),
                user.getFirstName(),
                token,
                Naming.AMQP.RESET_PASSWORD_TOKEN_ROUTING_KEY
        );

        resetPasswordTokenRepository.save(passwordResetToken);
    }

    @Transactional
    @Override
    public String changePassword(String resetPasswordToken, NewPasswordRequestDto newPasswordDto) {
        String hashedToken = Hashing.sha256().hashString(resetPasswordToken, StandardCharsets.UTF_8).toString();

        PasswordResetToken token = resetPasswordTokenRepository.findByToken(hashedToken)
                .orElseThrow(() -> new InvalidTokenException("Invalid Reset Password Token"));

        // check if token is expired

        if(token.getExpiresAt().isBefore(LocalDateTime.now())) throw new ExpiredTokenException("Token is expired!");

        log.info("token: " + token);

        User user = token.getUser();

        log.info("user: " + user);

        user.setPassword(passwordEncoder.encode(newPasswordDto.getPassword()));
        userRepository.save(user);

        // delete all password reset tokens for user
        resetPasswordTokenRepository.deleteAllByUserId(user.getId());

        return "password changed";
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // check if token exists
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        // exclude "Bearer"
        refreshToken = authHeader.substring(7);
        username = jwtService.getUsernameFromToken(refreshToken);

        // check if username is not null and if user is not authenticated
        if (username != null) {
            UserDetails userDetails = this.userRepository.findByUsername(username).orElseThrow();

            if (jwtService.validateToken(refreshToken, userDetails)){
                // generate new Access Token
                var accessToken = jwtService.generateToken(userDetails);
                var authResponse = AuthenticationResponseDto.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }

        }
    }

    private void sendEmail(
            String emailTo,
            String userName,
            String confirmationToken,
            String routingKey
    ) {

        AMQPTokenEmail confirmationEmail = new AMQPTokenEmail();
        confirmationEmail.setEmailTo(emailTo);
        confirmationEmail.setName(userName);
        confirmationEmail.setConfirmationToken(confirmationToken);

        try {
            rabbitTemplate.convertAndSend(
                    Naming.AMQP.TOKEN_EXCHANGE,
                    routingKey,
                    confirmationEmail
            );
            log.info("Email to {} has been successfully sent", emailTo);

        } catch (AmqpException e) {
            log.warn("Failed to send confirmation email to {}", emailTo);
            throw new RuntimeException("There was an error while trying to sent confirmation email");
        }
    }
}
