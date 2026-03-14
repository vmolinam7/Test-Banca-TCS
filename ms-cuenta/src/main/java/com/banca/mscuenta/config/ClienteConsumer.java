package com.banca.mscuenta.config;

import com.banca.mscuenta.event.ClienteCreadoEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ClienteConsumer {

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void recibirEvento(ClienteCreadoEvent event) {

        log.info("Cliente recibido desde ms-cliente: {}", event.getNombre());

    }

}