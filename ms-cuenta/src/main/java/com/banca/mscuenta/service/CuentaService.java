package com.banca.mscuenta.service;

import com.banca.mscuenta.dto.CuentaDTO;

import java.util.List;

public interface CuentaService {

    CuentaDTO crear(CuentaDTO dto);
    List<CuentaDTO> listar();
    CuentaDTO obtenerPorId(Long id);
    CuentaDTO actualizar(Long id, CuentaDTO dto);

    void eliminar(Long id);

    List<CuentaDTO> findByClienteId(Long clienteId);

}