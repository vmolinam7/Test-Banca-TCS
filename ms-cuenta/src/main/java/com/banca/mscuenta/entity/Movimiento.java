package com.banca.mscuenta.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimiento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movimientoId;

    private LocalDateTime fecha;

    private String tipoMovimiento;

    private BigDecimal valor;

    private BigDecimal saldo;

    private Long cuentaId;

}