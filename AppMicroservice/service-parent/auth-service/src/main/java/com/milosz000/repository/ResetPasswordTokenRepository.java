package com.milosz000.repository;

import com.milosz000.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResetPasswordTokenRepository extends JpaRepository<PasswordResetToken, Long> {
}
