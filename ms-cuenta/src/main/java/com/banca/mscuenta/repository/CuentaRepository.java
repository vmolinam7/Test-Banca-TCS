package com.banca.mscuenta.repository;

import com.banca.mscuenta.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CuentaRepository extends JpaRepository<Cuenta,Long> {
    List<Cuenta> findByClienteId(Long clienteId);
    boolean existsByNumeroCuenta(String numeroCuenta);
}