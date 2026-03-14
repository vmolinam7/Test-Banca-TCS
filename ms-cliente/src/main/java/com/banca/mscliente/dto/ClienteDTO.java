package com.banca.mscliente.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ClienteDTO {

    private Long clienteId;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min=3, max=100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @Min(value = 18, message = "Debe ser mayor de 18 años")
    private Integer edad;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    @NotBlank(message = "La identificación es obligatoria")
    private String identificacion;

    private String genero;

    @NotBlank(message = "La contraseña es obligatoria")
    private String contrasena;

    @NotNull(message = "El estado no puede ser nulo")
    private Boolean estado;

}