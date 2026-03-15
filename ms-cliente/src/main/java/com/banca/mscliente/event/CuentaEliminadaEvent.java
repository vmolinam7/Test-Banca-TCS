package com.banca.mscliente.event;

import lombok.Data;
import java.io.Serializable;

@Data
public class CuentaEliminadaEvent implements Serializable {

    private Long cuentaId;
    private Long clienteId;

}
