package com.banca.mscuenta.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MovimientoDTO {

    private Long movimientoId;

    private String tipoMovimiento;

    private BigDecimal valor;

    private Long cuentaId;

}