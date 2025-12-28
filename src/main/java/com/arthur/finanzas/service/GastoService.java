package com.arthur.finanzas.service;

import com.arthur.finanzas.dto.*;
import com.arthur.finanzas.exception.ResourceNotFoundException;
import com.arthur.finanzas.model.Gasto;
import com.arthur.finanzas.model.User;
import com.arthur.finanzas.repository.GastoRepository;
import com.arthur.finanzas.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GastoService {

    private final GastoRepository gastoRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<GastoDTO> getAllGastos(Long userId) {
        return gastoRepository.findByUserIdOrderByFechaDesc(userId)
                .stream()
                .map(gasto -> modelMapper.map(gasto, GastoDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public GastoDTO getGastoById(Long id, Long userId) {
        Gasto gasto = gastoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gasto no encontrado con id: " + id));

        // Verificar que el gasto pertenece al usuario
        if (!gasto.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Gasto no encontrado");
        }

        return modelMapper.map(gasto, GastoDTO.class);
    }

    @Transactional(readOnly = true)
    public List<GastoDTO> getGastosByDateRange(Long userId, LocalDate fechaInicio, LocalDate fechaFin) {
        return gastoRepository.findByUserIdAndFechaBetweenOrderByFechaDesc(userId, fechaInicio, fechaFin)
                .stream()
                .map(gasto -> modelMapper.map(gasto, GastoDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public GastoDTO createGasto(GastoCreateDTO gastoCreateDTO, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Gasto gasto = modelMapper.map(gastoCreateDTO, Gasto.class);
        gasto.setUser(user);

        Gasto savedGasto = gastoRepository.save(gasto);
        return modelMapper.map(savedGasto, GastoDTO.class);
    }

    @Transactional
    public GastoDTO updateGasto(Long id, GastoUpdateDTO gastoUpdateDTO, Long userId) {
        Gasto gasto = gastoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gasto no encontrado con id: " + id));

        // Verificar que el gasto pertenece al usuario
        if (!gasto.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Gasto no encontrado");
        }

        // Actualizar solo los campos no nulos
        if (gastoUpdateDTO.getConcepto() != null) {
            gasto.setConcepto(gastoUpdateDTO.getConcepto());
        }
        if (gastoUpdateDTO.getMonto() != null) {
            gasto.setMonto(gastoUpdateDTO.getMonto());
        }
        if (gastoUpdateDTO.getCategoria() != null) {
            gasto.setCategoria(gastoUpdateDTO.getCategoria());
        }
        if (gastoUpdateDTO.getFecha() != null) {
            gasto.setFecha(gastoUpdateDTO.getFecha());
        }
        if (gastoUpdateDTO.getMetodoPago() != null) {
            gasto.setMetodoPago(gastoUpdateDTO.getMetodoPago());
        }
        if (gastoUpdateDTO.getNotas() != null) {
            gasto.setNotas(gastoUpdateDTO.getNotas());
        }

        Gasto updatedGasto = gastoRepository.save(gasto);
        return modelMapper.map(updatedGasto, GastoDTO.class);
    }

    @Transactional
    public void deleteGasto(Long id, Long userId) {
        Gasto gasto = gastoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gasto no encontrado con id: " + id));

        // Verificar que el gasto pertenece al usuario
        if (!gasto.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Gasto no encontrado");
        }

        gastoRepository.delete(gasto);
    }

    @Transactional(readOnly = true)
    public EstadisticasGastosDTO getEstadisticas(Long userId) {
        List<Gasto> gastos = gastoRepository.findByUserIdOrderByFechaDesc(userId);

        BigDecimal total = gastos.stream()
                .map(Gasto::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long cantidad = gastos.size();

        BigDecimal promedio = cantidad > 0
                ? total.divide(BigDecimal.valueOf(cantidad), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        // Gastos por categoría
        Map<String, BigDecimal> porCategoria = gastos.stream()
                .collect(Collectors.groupingBy(
                        Gasto::getCategoria,
                        Collectors.reducing(BigDecimal.ZERO, Gasto::getMonto, BigDecimal::add)
                ));

        // Gastos por método de pago
        Map<String, BigDecimal> porMetodoPago = gastos.stream()
                .collect(Collectors.groupingBy(
                        Gasto::getMetodoPago,
                        Collectors.reducing(BigDecimal.ZERO, Gasto::getMonto, BigDecimal::add)
                ));

        return new EstadisticasGastosDTO(total, cantidad, promedio, porCategoria, porMetodoPago);
    }
}