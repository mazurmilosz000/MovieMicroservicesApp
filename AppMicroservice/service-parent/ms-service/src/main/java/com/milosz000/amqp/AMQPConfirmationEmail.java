package com.milosz000.amqp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AMQPConfirmationEmail {

    String emailTo;
    String name;
    String confirmationToken;
}
