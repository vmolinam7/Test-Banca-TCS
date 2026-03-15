package com.banca.mscuenta.config;

import com.banca.mscuenta.event.ClienteCreadoEvent;
import com.banca.mscuenta.event.ClienteEliminadoEvent;
import com.banca.mscuenta.entity.ClienteRef;
import com.banca.mscuenta.repository.ClienteRefRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ClienteConsumer {

    private final ClienteRefRepository clienteRefRepository;
    private final MessageConverter messageConverter;

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void recibirEvento(Message message) {
        Object event = messageConverter.fromMessage(message);

        if (event instanceof ClienteCreadoEvent creado) {
            ClienteRef ref = new ClienteRef(creado.getClienteId(), creado.getNombre());
            clienteRefRepository.save(ref);
            log.info("Cliente recibido desde ms-cliente: {}", creado.getNombre());
            return;
        }

        if (event instanceof ClienteEliminadoEvent eliminado) {
            clienteRefRepository.deleteById(eliminado.getClienteId());
            log.info("Cliente eliminado recibido desde ms-cliente: {}", eliminado.getClienteId());
            return;
        }

        log.warn("Evento de cliente no reconocido: {}", event == null ? "null" : event.getClass().getName());
    }

}
