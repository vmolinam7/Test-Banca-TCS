package com.banca.mscuenta.service;

import com.banca.mscuenta.dto.ReporteDTO;
import com.banca.mscuenta.entity.Cuenta;
import com.banca.mscuenta.entity.Movimiento;
import com.banca.mscuenta.repository.CuentaRepository;
import com.banca.mscuenta.repository.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;

    @Override
    public List<ReporteDTO> generarReporte(String fechaRango, Long clienteId) {
        // Parse date range (format expected: "yyyy-MM-dd,yyyy-MM-dd")
        String[] fechas = fechaRango.split(",");
        LocalDateTime fechaInicio;
        LocalDateTime fechaFin;

        if(fechas.length == 2) {
            fechaInicio = LocalDate.parse(fechas[0].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
            fechaFin = LocalDate.parse(fechas[1].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).atTime(23, 59, 59);
        } else {
            // If only one date is provided, bound it to that full day
            fechaInicio = LocalDate.parse(fechas[0].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
            fechaFin = LocalDate.parse(fechas[0].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).atTime(23, 59, 59);
        }

        List<Cuenta> cuentas = cuentaRepository.findByClienteId(clienteId);
        List<ReporteDTO> reporte = new ArrayList<>();

        for (Cuenta cuenta : cuentas) {
            List<Movimiento> movimientos = movimientoRepository.findByCuentaIdAndFechaBetween(cuenta.getCuentaId(), fechaInicio, fechaFin);
            
            ReporteDTO dto = new ReporteDTO();
            dto.setNumeroCuenta(cuenta.getNumeroCuenta());
            dto.setTipoCuenta(cuenta.getTipoCuenta());
            dto.setSaldoInicial(cuenta.getSaldoInicial());
            dto.setEstado(cuenta.getEstado());
            
            // The current available balance is determined by the last movement, or fallback to initial balance
            if (!movimientos.isEmpty()) {
                dto.setSaldoTotal(movimientos.get(movimientos.size() - 1).getSaldo());
            } else {
                dto.setSaldoTotal(cuenta.getSaldoInicial());
            }
            
            dto.setMovimientos(movimientos);
            reporte.add(dto);
        }

        return reporte;
    }
}
