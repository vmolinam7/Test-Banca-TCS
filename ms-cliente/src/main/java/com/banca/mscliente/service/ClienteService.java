package com.banca.mscliente.service;

import com.banca.mscliente.dto.ClienteDTO;
import java.util.List;

public interface ClienteService {

    ClienteDTO crear(ClienteDTO dto);

    List<ClienteDTO> listar();

    ClienteDTO obtenerPorId(Long id);
    ClienteDTO actualizar(Long id, ClienteDTO dto);

    void eliminar(Long id);

}