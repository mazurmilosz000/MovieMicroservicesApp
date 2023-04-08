package com.milosz000.service.impl;

import com.milosz000.model.ConfirmationToken;
import com.milosz000.repository.ConfirmationTokenRepository;
import com.milosz000.service.ConfirmationTokenService;
import com.milosz000.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }


}
