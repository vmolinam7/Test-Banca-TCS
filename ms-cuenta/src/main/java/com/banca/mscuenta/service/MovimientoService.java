package com.banca.mscuenta.service;

import com.banca.mscuenta.dto.MovimientoDTO;
import java.util.List;

public interface MovimientoService {

    void registrarMovimiento(MovimientoDTO dto);

    List<MovimientoDTO> listar();

    MovimientoDTO actualizar(Long id, MovimientoDTO dto);

    void eliminar(Long id);

}