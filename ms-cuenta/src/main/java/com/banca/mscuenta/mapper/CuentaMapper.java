package com.banca.mscuenta.mapper;

import com.banca.mscuenta.dto.CuentaDTO;
import com.banca.mscuenta.entity.Cuenta;

public class CuentaMapper {

    public static CuentaDTO toDTO(Cuenta entity){

        CuentaDTO dto = new CuentaDTO();

        dto.setCuentaId(entity.getCuentaId());
        dto.setNumeroCuenta(entity.getNumeroCuenta());
        dto.setTipoCuenta(entity.getTipoCuenta());
        dto.setSaldoInicial(entity.getSaldoInicial());
        dto.setSaldoActual(entity.getSaldoActual());
        dto.setEstado(entity.getEstado());
        dto.setClienteId(entity.getClienteId());

        return dto;
    }

    public static Cuenta toEntity(CuentaDTO dto){

        Cuenta cuenta = new Cuenta();

        cuenta.setCuentaId(dto.getCuentaId());
        cuenta.setNumeroCuenta(dto.getNumeroCuenta());
        cuenta.setTipoCuenta(dto.getTipoCuenta());
        cuenta.setSaldoInicial(dto.getSaldoInicial());
        cuenta.setSaldoActual(dto.getSaldoActual());
        cuenta.setEstado(dto.getEstado());
        cuenta.setClienteId(dto.getClienteId());

        return cuenta;
    }

}