package com.banca.mscliente.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name = "cliente_id")
public class Cliente extends Persona {

    private String contrasena;

    private Boolean estado;

}