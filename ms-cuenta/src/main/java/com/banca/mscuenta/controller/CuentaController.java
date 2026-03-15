package com.banca.mscuenta.controller;

import com.banca.mscuenta.dto.CuentaDTO;
import com.banca.mscuenta.service.CuentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaService service;

    @PostMapping
    public CuentaDTO crear(@Valid @RequestBody CuentaDTO dto){
        return service.crear(dto);
    }

    @GetMapping
    public List<CuentaDTO> listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    public CuentaDTO obtenerPorId(@PathVariable Long id){
        return service.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public CuentaDTO actualizar(@PathVariable Long id, @Valid @RequestBody CuentaDTO dto){
        return service.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        service.eliminar(id);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<CuentaDTO> findByClienteId(@PathVariable Long clienteId) {
        return service.findByClienteId(clienteId);
    }

}
