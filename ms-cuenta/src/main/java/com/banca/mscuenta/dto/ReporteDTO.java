package com.banca.mscuenta.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReporteDTO {

    private String numeroCuenta;

    private String tipoCuenta;

    private BigDecimal saldoInicial;

    private Boolean estado;

    private BigDecimal saldoTotal;

    private List<com.banca.mscuenta.entity.Movimiento> movimientos;

}