package com.banca.mscuenta.config;

import com.banca.mscuenta.event.CuentaCreadaEvent;
import com.banca.mscuenta.event.CuentaEliminadaEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CuentaProducer {

    private final RabbitTemplate rabbitTemplate;

    public void enviarCuentaCreada(CuentaCreadaEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.CUENTA_EXCHANGE,
                RabbitConfig.CUENTA_ROUTING,
                event
        );
    }

    public void enviarCuentaEliminada(CuentaEliminadaEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.CUENTA_EXCHANGE,
                RabbitConfig.CUENTA_ROUTING,
                event
        );
    }

}
