package com.banca.mscliente.event;

import lombok.Data;
import java.io.Serializable;

@Data
public class ClienteCreadoEvent implements Serializable {

    private Long clienteId;
    private String nombre;

}