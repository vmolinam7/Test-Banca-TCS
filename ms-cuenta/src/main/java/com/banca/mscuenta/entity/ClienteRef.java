package com.banca.mscuenta.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "cliente_ref")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRef {

    @Id
    private Long clienteId;

    @Column(nullable = false)
    private String nombre;

}
