package com.banca.mscuenta.mapper;

import com.banca.mscuenta.dto.MovimientoDTO;
import com.banca.mscuenta.entity.Movimiento;

public class MovimientoMapper {

    public static Movimiento toEntity(MovimientoDTO dto){

        Movimiento m = new Movimiento();

        m.setMovimientoId(dto.getMovimientoId());
        m.setTipoMovimiento(dto.getTipoMovimiento());
        m.setValor(dto.getValor());
        m.setCuentaId(dto.getCuentaId());

        return m;
    }

    public static MovimientoDTO toDTO(Movimiento entity){

        MovimientoDTO dto = new MovimientoDTO();

        dto.setMovimientoId(entity.getMovimientoId());
        dto.setTipoMovimiento(entity.getTipoMovimiento());
        dto.setValor(entity.getValor());
        dto.setCuentaId(entity.getCuentaId());

        return dto;
    }

}