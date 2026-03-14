package com.banca.mscuenta.service;

import com.banca.mscuenta.dto.MovimientoDTO;
import com.banca.mscuenta.entity.Cuenta;
import com.banca.mscuenta.entity.Movimiento;
import com.banca.mscuenta.exception.SaldoNoDisponibleException;
import com.banca.mscuenta.exception.NotFoundException;
import com.banca.mscuenta.mapper.MovimientoMapper;
import com.banca.mscuenta.repository.CuentaRepository;
import com.banca.mscuenta.repository.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;

    @Override
    public void registrarMovimiento(MovimientoDTO dto) {

        Cuenta cuenta = cuentaRepository.findById(dto.getCuentaId())
                .orElseThrow(() -> new NotFoundException("Cuenta no encontrada con ID: " + dto.getCuentaId()));

        if (dto.getValor().compareTo(BigDecimal.ZERO) < 0) {
            dto.setTipoMovimiento("retiro");
        } else {
            dto.setTipoMovimiento("deposito");
        }

        BigDecimal saldoCalculo = cuenta.getSaldoActual() != null ? cuenta.getSaldoActual() : cuenta.getSaldoInicial();

        BigDecimal nuevoSaldo = saldoCalculo.add(dto.getValor());

        if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new SaldoNoDisponibleException("Saldo no disponible");
        }

        cuenta.setSaldoActual(nuevoSaldo);

        cuentaRepository.save(cuenta);

        Movimiento movimiento = MovimientoMapper.toEntity(dto);

        movimiento.setFecha(LocalDateTime.now());
        movimiento.setSaldo(nuevoSaldo);

        movimientoRepository.save(movimiento);
    }

    @Override
    public List<MovimientoDTO> listar() {
        return movimientoRepository.findAll()
                .stream()
                .map(MovimientoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MovimientoDTO actualizar(Long id, MovimientoDTO dto) {
        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Movimiento no encontrado con ID: " + id));

        if (dto.getValor().compareTo(BigDecimal.ZERO) < 0) {
            movimiento.setTipoMovimiento("retiro");
        } else {
            movimiento.setTipoMovimiento("deposito");
        }
        movimiento.setValor(dto.getValor());

        movimientoRepository.save(movimiento);
        return MovimientoMapper.toDTO(movimiento);
    }

    @Override
    public void eliminar(Long id) {
        movimientoRepository.deleteById(id);
    }
}
