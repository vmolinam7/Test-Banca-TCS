package com.banca.mscliente.event;

import lombok.Data;
import java.io.Serializable;

@Data
public class ClienteEliminadoEvent implements Serializable {

    private Long clienteId;

}
