package com.milosz000.amqp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AMQPTokenEmail {

    String emailTo;
    String name;
    String confirmationToken;
}
