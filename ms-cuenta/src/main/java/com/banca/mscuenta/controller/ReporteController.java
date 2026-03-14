package com.banca.mscuenta.controller;

import com.banca.mscuenta.dto.ReporteDTO;
import com.banca.mscuenta.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping
    public ResponseEntity<List<ReporteDTO>> reporte(@RequestParam String fecha, @RequestParam Long clienteId) {
        return ResponseEntity.ok(reporteService.generarReporte(fecha, clienteId));
    }

}