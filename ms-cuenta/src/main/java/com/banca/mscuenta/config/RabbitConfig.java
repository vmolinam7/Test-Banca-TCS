package com.banca.mscuenta.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {

    public static final String QUEUE = "cliente.queue";
    public static final String CUENTA_EXCHANGE = "cuenta.exchange";
    public static final String CUENTA_ROUTING = "cuenta.routing";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE);
    }

    @Bean
    public TopicExchange cuentaExchange() {
        return new TopicExchange(CUENTA_EXCHANGE);
    }

    @Bean
    public MessageConverter converter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("*");
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("com.banca.mscliente.event.ClienteCreadoEvent",
                com.banca.mscuenta.event.ClienteCreadoEvent.class);
        idClassMapping.put("com.banca.mscliente.event.ClienteEliminadoEvent",
                com.banca.mscuenta.event.ClienteEliminadoEvent.class);
        classMapper.setIdClassMapping(idClassMapping);
        converter.setClassMapper(classMapper);
        return converter;
    }

}
