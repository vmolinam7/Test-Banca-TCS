package com.banca.mscliente.service;

import com.banca.mscliente.config.ClienteProducer;
import com.banca.mscliente.dto.ClienteDTO;
import com.banca.mscliente.entity.Cliente;
import com.banca.mscliente.event.ClienteCreadoEvent;
import com.banca.mscliente.mapper.ClienteMapper;
import com.banca.mscliente.exception.BusinessException;
import com.banca.mscliente.exception.NotFoundException;
import com.banca.mscliente.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repository;
    private final ClienteProducer producer;

    @Override
    public ClienteDTO crear(ClienteDTO dto) {

        if (dto.getIdentificacion() != null && repository.existsByIdentificacion(dto.getIdentificacion())) {
            throw new BusinessException("El cliente con identificación " + dto.getIdentificacion() + " ya existe.");
        }

        if (dto.getTelefono() != null && repository.existsByTelefono(dto.getTelefono())) {
            throw new BusinessException("El cliente con el teléfono " + dto.getTelefono() + " ya existe.");
        }

        Cliente cliente = ClienteMapper.toEntity(dto);

        repository.save(cliente);

        ClienteCreadoEvent event = new ClienteCreadoEvent();

        event.setClienteId(cliente.getId());
        event.setNombre(cliente.getNombre());

        producer.enviarEvento(event);

        return ClienteMapper.toDTO(cliente);
    }

    @Override
    public List<ClienteDTO> listar() {

        return repository.findAll()
                .stream()
                .map(ClienteMapper::toDTO)
                .collect(Collectors.toList());

    }

    @Override
    public ClienteDTO obtenerPorId(Long id) {
        return repository.findById(id)
                .map(ClienteMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado con ID: " + id));
    }

    @Override
    public ClienteDTO actualizar(Long id, ClienteDTO dto) {

        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado con ID: " + id));

        if (dto.getTelefono() != null && !dto.getTelefono().equals(cliente.getTelefono())
                && repository.existsByTelefono(dto.getTelefono())) {
            throw new BusinessException("El teléfono " + dto.getTelefono() + " ya está en uso por otro cliente.");
        }

        if (dto.getIdentificacion() != null && !dto.getIdentificacion().equals(cliente.getIdentificacion())
                && repository.existsByIdentificacion(dto.getIdentificacion())) {
            throw new BusinessException(
                    "La identificación " + dto.getIdentificacion() + " ya está en uso por otro cliente.");
        }

        cliente.setNombre(dto.getNombre());
        cliente.setGenero(dto.getGenero());
        cliente.setEdad(dto.getEdad());
        cliente.setIdentificacion(dto.getIdentificacion());
        cliente.setDireccion(dto.getDireccion());
        cliente.setTelefono(dto.getTelefono());
        cliente.setContrasena(dto.getContrasena());
        cliente.setEstado(dto.getEstado());

        repository.save(cliente);

        return ClienteMapper.toDTO(cliente);
    }

    @Override
    public void eliminar(Long id) {

        if (!repository.existsById(id)) {
            throw new NotFoundException("Cliente no encontrado con ID: " + id);
        }

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://ms-cuenta:8082/cuentas/cliente/" + id;

        List<?> accounts = null;
        try {
            accounts = restTemplate.getForObject(url, List.class);
        } catch (Exception e) {
        }

        if (accounts != null && !accounts.isEmpty()) {
            throw new BusinessException("No se puede eliminar el cliente porque tiene cuentas asociadas.");
        }

        repository.deleteById(id);

    }
}