package com.banca.mscuenta.repository;

import com.banca.mscuenta.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import java.time.LocalDateTime;

public interface MovimientoRepository extends JpaRepository<Movimiento,Long> {

    List<Movimiento> findByCuentaId(Long cuentaId);

    List<Movimiento> findByCuentaIdAndFechaBetween(Long cuentaId, LocalDateTime fechaInicio, LocalDateTime fechaFin);

}