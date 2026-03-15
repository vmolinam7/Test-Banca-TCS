package com.banca.mscliente.config;

import com.banca.mscliente.entity.ClienteCuentaRef;
import com.banca.mscliente.event.CuentaCreadaEvent;
import com.banca.mscliente.event.CuentaEliminadaEvent;
import com.banca.mscliente.repository.ClienteCuentaRefRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CuentaConsumer {

    private final ClienteCuentaRefRepository clienteCuentaRefRepository;

    @RabbitListener(queues = RabbitConfig.CUENTA_QUEUE)
    public void recibirCuentaCreada(CuentaCreadaEvent event) {
        ClienteCuentaRef ref = clienteCuentaRefRepository.findById(event.getClienteId())
                .orElseGet(() -> new ClienteCuentaRef(event.getClienteId(), 0));
        ref.setCuentasCount(ref.getCuentasCount() + 1);
        clienteCuentaRefRepository.save(ref);
        log.info("Cuenta creada recibida: cuentaId={}, clienteId={}", event.getCuentaId(), event.getClienteId());
    }

    @RabbitListener(queues = RabbitConfig.CUENTA_QUEUE)
    public void recibirCuentaEliminada(CuentaEliminadaEvent event) {
        clienteCuentaRefRepository.findById(event.getClienteId()).ifPresent(ref -> {
            int next = Math.max(0, ref.getCuentasCount() - 1);
            if (next == 0) {
                clienteCuentaRefRepository.delete(ref);
            } else {
                ref.setCuentasCount(next);
                clienteCuentaRefRepository.save(ref);
            }
        });
        log.info("Cuenta eliminada recibida: cuentaId={}, clienteId={}", event.getCuentaId(), event.getClienteId());
    }

}
