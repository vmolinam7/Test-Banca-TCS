package com.banca.mscliente.config;

import com.banca.mscliente.event.ClienteCreadoEvent;
import com.banca.mscliente.event.ClienteEliminadoEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClienteProducer {

    private final RabbitTemplate rabbitTemplate;

    public void enviarClienteCreado(ClienteCreadoEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE,
                RabbitConfig.ROUTING,
                event
        );
    }

    public void enviarClienteEliminado(ClienteEliminadoEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE,
                RabbitConfig.ROUTING,
                event
        );
    }

}
