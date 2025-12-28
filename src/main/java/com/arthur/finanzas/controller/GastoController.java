package com.arthur.finanzas.controller;

import com.arthur.finanzas.dto.*;
import com.arthur.finanzas.service.GastoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/gastos")
@RequiredArgsConstructor
@Tag(name = "Gastos", description = "API para gestión de gastos")
public class GastoController {

    private final GastoService gastoService;

    @GetMapping
    @Operation(summary = "Obtener todos los gastos del usuario")
    public ResponseEntity<List<GastoDTO>> getAllGastos(
            @RequestParam(defaultValue = "1") Long userId) {
        List<GastoDTO> gastos = gastoService.getAllGastos(userId);
        return ResponseEntity.ok(gastos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un gasto por ID")
    public ResponseEntity<GastoDTO> getGastoById(
            @PathVariable Long id,
            @RequestParam(defaultValue = "1") Long userId) {
        GastoDTO gasto = gastoService.getGastoById(id, userId);
        return ResponseEntity.ok(gasto);
    }

    @GetMapping("/rango-fechas")
    @Operation(summary = "Obtener gastos por rango de fechas")
    public ResponseEntity<List<GastoDTO>> getGastosByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam(defaultValue = "1") Long userId) {
        List<GastoDTO> gastos = gastoService.getGastosByDateRange(userId, fechaInicio, fechaFin);
        return ResponseEntity.ok(gastos);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo gasto")
    public ResponseEntity<GastoDTO> createGasto(
            @Valid @RequestBody GastoCreateDTO gastoCreateDTO,
            @RequestParam(defaultValue = "1") Long userId) {
        GastoDTO nuevoGasto = gastoService.createGasto(gastoCreateDTO, userId);
        return new ResponseEntity<>(nuevoGasto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un gasto existente")
    public ResponseEntity<GastoDTO> updateGasto(
            @PathVariable Long id,
            @Valid @RequestBody GastoUpdateDTO gastoUpdateDTO,
            @RequestParam(defaultValue = "1") Long userId) {
        GastoDTO gastoActualizado = gastoService.updateGasto(id, gastoUpdateDTO, userId);
        return ResponseEntity.ok(gastoActualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un gasto")
    public ResponseEntity<Void> deleteGasto(
            @PathVariable Long id,
            @RequestParam(defaultValue = "1") Long userId) {
        gastoService.deleteGasto(id, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estadisticas")
    @Operation(summary = "Obtener estadísticas de gastos")
    public ResponseEntity<EstadisticasGastosDTO> getEstadisticas(
            @RequestParam(defaultValue = "1") Long userId) {
        EstadisticasGastosDTO estadisticas = gastoService.getEstadisticas(userId);
        return ResponseEntity.ok(estadisticas);
    }
}