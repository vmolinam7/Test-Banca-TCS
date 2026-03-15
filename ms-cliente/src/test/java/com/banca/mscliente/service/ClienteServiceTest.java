package com.banca.mscliente.service;

import com.banca.mscliente.config.ClienteProducer;
import com.banca.mscliente.dto.ClienteDTO;
import com.banca.mscliente.entity.Cliente;
import com.banca.mscliente.repository.ClienteCuentaRefRepository;
import com.banca.mscliente.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ClienteServiceTest {

    @Test
    void crearClienteTest() {

        ClienteRepository repository = Mockito.mock(ClienteRepository.class);
        ClienteProducer producer = Mockito.mock(ClienteProducer.class);
        ClienteCuentaRefRepository clienteCuentaRefRepository = Mockito.mock(ClienteCuentaRefRepository.class);

        when(repository.existsByIdentificacion(any())).thenReturn(false);
        when(repository.existsByTelefono(any())).thenReturn(false);

        Cliente saved = new Cliente();
        saved.setId(1L);
        saved.setNombre("Jose");
        saved.setEstado(true);

        when(repository.save(any(Cliente.class))).thenReturn(saved);

        ClienteServiceImpl service = new ClienteServiceImpl(repository, producer, clienteCuentaRefRepository);

        ClienteDTO dto = new ClienteDTO();
        dto.setNombre("Jose");
        dto.setEdad(30);
        dto.setDireccion("Quito");
        dto.setTelefono("0999999999");
        dto.setIdentificacion("0102030405");
        dto.setGenero("M");
        dto.setContrasena("secret");
        dto.setEstado(true);

        ClienteDTO result = service.crear(dto);

        assertNotNull(result);

    }

}
