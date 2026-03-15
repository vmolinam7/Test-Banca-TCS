package com.banca.mscuenta.service;

import com.banca.mscuenta.config.CuentaProducer;
import com.banca.mscuenta.dto.CuentaDTO;
import com.banca.mscuenta.entity.Cuenta;
import com.banca.mscuenta.event.CuentaCreadaEvent;
import com.banca.mscuenta.event.CuentaEliminadaEvent;
import com.banca.mscuenta.exception.BusinessException;
import com.banca.mscuenta.exception.NotFoundException;
import com.banca.mscuenta.mapper.CuentaMapper;
import com.banca.mscuenta.repository.ClienteRefRepository;
import com.banca.mscuenta.repository.CuentaRepository;
import com.banca.mscuenta.repository.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CuentaServiceImpl implements CuentaService {

    private final CuentaRepository repository;
    private final MovimientoRepository movimientoRepository;
    private final ClienteRefRepository clienteRefRepository;
    private final CuentaProducer cuentaProducer;

    @Override
    public CuentaDTO crear(CuentaDTO dto) {

        if (repository.existsByNumeroCuenta(dto.getNumeroCuenta())) {
            throw new BusinessException("El numero de cuenta ya existe: " + dto.getNumeroCuenta());
        }

        if (!clienteRefRepository.existsById(dto.getClienteId())) {
            throw new BusinessException("El cliente con ID " + dto.getClienteId() + " no existe.");
        }

        Cuenta cuenta = CuentaMapper.toEntity(dto);
        cuenta.setSaldoActual(dto.getSaldoInicial());

        repository.save(cuenta);

        CuentaCreadaEvent event = new CuentaCreadaEvent();
        event.setCuentaId(cuenta.getCuentaId());
        event.setClienteId(cuenta.getClienteId());
        cuentaProducer.enviarCuentaCreada(event);

        return CuentaMapper.toDTO(cuenta);
    }

    @Override
    public List<CuentaDTO> listar() {
        return repository.findAll()
                .stream()
                .map(CuentaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CuentaDTO obtenerPorId(Long id) {
        return repository.findById(id)
                .map(CuentaMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Cuenta no encontrada con ID: " + id));
    }

    @Override
    public CuentaDTO actualizar(Long id, CuentaDTO dto) {

        Cuenta cuenta = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cuenta no encontrada con ID: " + id));

        if (!clienteRefRepository.existsById(dto.getClienteId())) {
            throw new BusinessException("El cliente con ID " + dto.getClienteId() + " no existe.");
        }

        cuenta.setNumeroCuenta(dto.getNumeroCuenta());
        cuenta.setTipoCuenta(dto.getTipoCuenta());
        cuenta.setEstado(dto.getEstado());
        cuenta.setSaldoInicial(dto.getSaldoInicial());
        cuenta.setClienteId(dto.getClienteId());

        repository.save(cuenta);

        return CuentaMapper.toDTO(cuenta);

    }

    @Override
    public void eliminar(Long id) {

        if (!movimientoRepository.findByCuentaId(id).isEmpty()) {
            throw new BusinessException("No se puede eliminar la cuenta porque tiene movimientos asociados.");
        }

        Cuenta cuenta = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cuenta no encontrada con ID: " + id));

        repository.deleteById(id);

        CuentaEliminadaEvent event = new CuentaEliminadaEvent();
        event.setCuentaId(cuenta.getCuentaId());
        event.setClienteId(cuenta.getClienteId());
        cuentaProducer.enviarCuentaEliminada(event);

    }

    @Override
    public List<CuentaDTO> findByClienteId(Long clienteId) {
        return repository.findByClienteId(clienteId)
                .stream()
                .map(CuentaMapper::toDTO)
                .collect(Collectors.toList());
    }
}
