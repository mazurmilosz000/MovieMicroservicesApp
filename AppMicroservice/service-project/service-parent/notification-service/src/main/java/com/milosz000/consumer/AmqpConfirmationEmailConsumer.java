package com.milosz000.consumer;

import com.milosz000.amqp.AMQPTokenEmail;
import com.milosz000.constants.Naming;
import com.milosz000.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AmqpConfirmationEmailConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = Naming.AMQP.CONFIRMATION_TOKEN_QUEUE)
    public void receiveMessage(final AMQPTokenEmail message) {
        log.info("Sending confirmation email to: " + message.getEmailTo());
        emailService.sendConfirmationEmail(message.getEmailTo(), message.getName(), message.getConfirmationToken());

    }
}
