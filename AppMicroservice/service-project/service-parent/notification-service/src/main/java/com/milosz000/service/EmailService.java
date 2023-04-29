package com.milosz000.service;

public interface EmailService {
    void sendConfirmationEmail(String emailTo, String name, String token);

    void sendResetPasswordEmail(String emailTo, String name, String token);
}
