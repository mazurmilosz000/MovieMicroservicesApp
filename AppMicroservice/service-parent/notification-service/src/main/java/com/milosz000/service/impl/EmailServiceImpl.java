package com.milosz000.service.impl;

import com.milosz000.service.EmailService;
import com.milosz000.templates.EmailTemplates;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);
    private final JavaMailSender mailSender;

    @Override
    public void sendConfirmationEmail(String emailTo, String name, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            String confirmationLink = "http://localhost:8083/auth/confirm?token=" + token;

            message.setTo(emailTo);
            message.setSubject("Confirm your account");
            message.setFrom("no-reply@movie-service.com");
            message.setText(EmailTemplates.getConfirmationMailTemplate(name, confirmationLink));

            mailSender.send(message);
            LOGGER.info("Email send correctly to: " + emailTo);

        } catch (Exception e) {
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }
    }
}
