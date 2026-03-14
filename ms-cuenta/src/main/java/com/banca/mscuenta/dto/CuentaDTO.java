package com.banca.mscuenta.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CuentaDTO {

    private Long cuentaId;

    @NotBlank
    private String numeroCuenta;

    private String tipoCuenta;

    private BigDecimal saldoInicial;

    private BigDecimal saldoActual;

    private Boolean estado;

    private Long clienteId;

}