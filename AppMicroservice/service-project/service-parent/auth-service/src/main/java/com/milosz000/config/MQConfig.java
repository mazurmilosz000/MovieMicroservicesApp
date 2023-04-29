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
    public Queue queue() {
        return new Queue(Naming.AMQP.CONFIRMATION_TOKEN_QUEUE);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(Naming.AMQP.CONFIRMATION_TOKEN_EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queue)
                .to(topicExchange)
                .with(Naming.AMQP.CONFIRMATION_TOKEN_ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return  template;
    }
}
