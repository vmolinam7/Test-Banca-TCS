package com.banca.mscliente.repository;

import com.banca.mscliente.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {
    boolean existsByIdentificacion(String identificacion);
    boolean existsByTelefono(String telefono);
}