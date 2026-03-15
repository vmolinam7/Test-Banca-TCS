package com.banca.mscliente.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cliente_cuenta_ref")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteCuentaRef {

    @Id
    private Long clienteId;

    @Column(nullable = false)
    private Integer cuentasCount;

}
