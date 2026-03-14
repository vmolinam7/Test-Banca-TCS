package com.banca.mscliente.config;

import com.banca.mscliente.event.ClienteCreadoEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClienteProducer {

    private final RabbitTemplate rabbitTemplate;

    public void enviarEvento(ClienteCreadoEvent event){

        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE,
                RabbitConfig.ROUTING,
                event
        );

    }

}