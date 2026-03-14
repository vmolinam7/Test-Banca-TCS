package com.banca.mscliente.mapper;

import com.banca.mscliente.dto.ClienteDTO;
import com.banca.mscliente.entity.Cliente;

public class ClienteMapper {

    public static ClienteDTO toDTO(Cliente cliente){
        ClienteDTO dto = new ClienteDTO();

        dto.setClienteId(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setEdad(cliente.getEdad());
        dto.setDireccion(cliente.getDireccion());
        dto.setTelefono(cliente.getTelefono());
        dto.setIdentificacion(cliente.getIdentificacion());
        dto.setGenero(cliente.getGenero());
        dto.setContrasena(cliente.getContrasena());
        dto.setEstado(cliente.getEstado());

        return dto;
    }

    public static Cliente toEntity(ClienteDTO dto){
        Cliente cliente = new Cliente();

        cliente.setId(dto.getClienteId());
        cliente.setNombre(dto.getNombre());
        cliente.setEdad(dto.getEdad());
        cliente.setDireccion(dto.getDireccion());
        cliente.setTelefono(dto.getTelefono());
        cliente.setIdentificacion(dto.getIdentificacion());
        cliente.setGenero(dto.getGenero());
        cliente.setContrasena(dto.getContrasena());
        cliente.setEstado(dto.getEstado());

        return cliente;
    }

}