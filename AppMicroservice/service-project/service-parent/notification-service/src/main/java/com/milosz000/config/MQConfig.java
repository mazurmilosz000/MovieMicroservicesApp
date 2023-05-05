package com.milosz000.config;

import com.milosz000.constants.Naming;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    @Bean
    public Queue confirmationQueue() {
        return new Queue(Naming.AMQP.CONFIRMATION_TOKEN_QUEUE);
    }

    @Bean
    public Queue resetPasswordQueue() {return new Queue(Naming.AMQP.RESET_PASSWORD_TOKEN_QUEUE);}

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(Naming.AMQP.TOKEN_EXCHANGE);
    }

    @Bean
    public Binding confirmationBinding(Queue confirmationQueue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(confirmationQueue)
                .to(topicExchange)
                .with(Naming.AMQP.CONFIRMATION_TOKEN_ROUTING_KEY);
    }

    @Bean
    public Binding resetPasswordBinding(Queue resetPasswordQueue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(resetPasswordQueue)
                .to(topicExchange)
                .with(Naming.AMQP.RESET_PASSWORD_TOKEN_ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return  template;
    }
}
