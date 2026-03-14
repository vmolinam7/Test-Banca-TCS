package com.banca.mscuenta.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cuenta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cuentaId;

    @Column(nullable = false, unique = true)
    private String numeroCuenta;

    private String tipoCuenta;

    private BigDecimal saldoInicial;

    private BigDecimal saldoActual;

    private Boolean estado;

    private Long clienteId;

}