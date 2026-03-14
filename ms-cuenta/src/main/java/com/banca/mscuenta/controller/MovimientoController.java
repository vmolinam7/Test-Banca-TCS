package com.banca.mscuenta.controller;

import com.banca.mscuenta.dto.MovimientoDTO;
import com.banca.mscuenta.service.MovimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoService service;

    @PostMapping
    public ResponseEntity<Void> registrar(@RequestBody MovimientoDTO dto){
        service.registrarMovimiento(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<MovimientoDTO>> listar(){
        return ResponseEntity.ok(service.listar());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovimientoDTO> actualizar(@PathVariable Long id, @RequestBody MovimientoDTO dto){
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}