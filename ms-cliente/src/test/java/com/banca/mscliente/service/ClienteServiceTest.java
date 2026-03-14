package com.banca.mscliente.service;

import com.banca.mscliente.dto.ClienteDTO;
import com.banca.mscliente.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ClienteServiceTest {

    @Test
    void crearClienteTest() {

        ClienteRepository repository = Mockito.mock(ClienteRepository.class);

        ClienteServiceImpl service = new ClienteServiceImpl(repository,null);

        ClienteDTO dto = new ClienteDTO();
        dto.setNombre("Jose");
        dto.setEdad(30);
        dto.setEstado(true);

        ClienteDTO result = service.crear(dto);

        assertNotNull(result);

    }

}