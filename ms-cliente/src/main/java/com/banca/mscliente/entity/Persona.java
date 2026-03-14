package com.banca.mscliente.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "persona")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String genero;

    private Integer edad;

    @Column(unique = true)
    private String identificacion;

    private String direccion;

    @Column(unique = true)
    private String telefono;

}