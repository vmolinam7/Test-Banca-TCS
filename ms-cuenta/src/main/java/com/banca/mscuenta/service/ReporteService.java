package com.banca.mscuenta.service;

import com.banca.mscuenta.dto.ReporteDTO;
import java.util.List;

public interface ReporteService {
    List<ReporteDTO> generarReporte(String fechaRango, Long clienteId);
}
