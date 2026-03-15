package com.banca.mscliente.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {

    public static final String QUEUE = "cliente.queue";
    public static final String EXCHANGE = "cliente.exchange";
    public static final String ROUTING = "cliente.routing";

    public static final String CUENTA_QUEUE = "cuenta.queue";
    public static final String CUENTA_EXCHANGE = "cuenta.exchange";
    public static final String CUENTA_ROUTING = "cuenta.routing";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(ROUTING);
    }

    @Bean
    public Queue cuentaQueue() {
        return new Queue(CUENTA_QUEUE);
    }

    @Bean
    public TopicExchange cuentaExchange() {
        return new TopicExchange(CUENTA_EXCHANGE);
    }

    @Bean
    public Binding cuentaBinding(Queue cuentaQueue, TopicExchange cuentaExchange) {
        return BindingBuilder
                .bind(cuentaQueue)
                .to(cuentaExchange)
                .with(CUENTA_ROUTING);
    }

    @Bean
    public MessageConverter converter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("*");
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("com.banca.mscuenta.event.CuentaCreadaEvent",
                com.banca.mscliente.event.CuentaCreadaEvent.class);
        idClassMapping.put("com.banca.mscuenta.event.CuentaEliminadaEvent",
                com.banca.mscliente.event.CuentaEliminadaEvent.class);
        idClassMapping.put("com.banca.mscliente.event.ClienteCreadoEvent",
                com.banca.mscliente.event.ClienteCreadoEvent.class);
        idClassMapping.put("com.banca.mscliente.event.ClienteEliminadoEvent",
                com.banca.mscliente.event.ClienteEliminadoEvent.class);
        classMapper.setIdClassMapping(idClassMapping);
        converter.setClassMapper(classMapper);
        return converter;
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

}
