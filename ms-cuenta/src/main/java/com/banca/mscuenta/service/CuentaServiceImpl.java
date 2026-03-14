package com.banca.mscuenta.service;

import com.banca.mscuenta.dto.CuentaDTO;
import com.banca.mscuenta.entity.Cuenta;
import com.banca.mscuenta.mapper.CuentaMapper;
import com.banca.mscuenta.exception.BusinessException;
import com.banca.mscuenta.exception.NotFoundException;
import com.banca.mscuenta.repository.CuentaRepository;
import com.banca.mscuenta.repository.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CuentaServiceImpl implements CuentaService {

    private final CuentaRepository repository;
    private final MovimientoRepository movimientoRepository;

    @Override
    public CuentaDTO crear(CuentaDTO dto) {

        if (repository.existsByNumeroCuenta(dto.getNumeroCuenta())) {
            throw new BusinessException("El número de cuenta ya existe: " + dto.getNumeroCuenta());
        }

        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getForObject("http://ms-cliente:8081/clientes/" + dto.getClienteId(), Object.class);
        } catch (Exception e) {
            throw new BusinessException(
                    "El cliente con ID " + dto.getClienteId() + " no existe o el servicio no está disponible.");
        }

        Cuenta cuenta = CuentaMapper.toEntity(dto);
        cuenta.setSaldoActual(dto.getSaldoInicial());

        repository.save(cuenta);

        return CuentaMapper.toDTO(cuenta);
    }

    @Override
    public List<CuentaDTO> listar() {
        return repository.findAll()
                .stream()
                .map(CuentaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CuentaDTO obtenerPorId(Long id) {
        return repository.findById(id)
                .map(CuentaMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Cuenta no encontrada con ID: " + id));
    }

    @Override
    public CuentaDTO actualizar(Long id, CuentaDTO dto) {

        Cuenta cuenta = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cuenta no encontrada con ID: " + id));

        cuenta.setTipoCuenta(dto.getTipoCuenta());
        cuenta.setEstado(dto.getEstado());
        cuenta.setSaldoInicial(dto.getSaldoInicial());

        repository.save(cuenta);

        return CuentaMapper.toDTO(cuenta);

    }

    @Override
    public void eliminar(Long id) {

        if (!movimientoRepository.findByCuentaId(id).isEmpty()) {
            throw new BusinessException("No se puede eliminar la cuenta porque tiene movimientos asociados.");
        }

        if (!repository.existsById(id)) {
            throw new NotFoundException("Cuenta no encontrada con ID: " + id);
        }

        repository.deleteById(id);

    }

    @Override
    public List<CuentaDTO> findByClienteId(Long clienteId) {
        return repository.findByClienteId(clienteId)
                .stream()
                .map(CuentaMapper::toDTO)
                .collect(Collectors.toList());
    }
}