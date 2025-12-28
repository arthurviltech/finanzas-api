package com.arthur.finanzas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadisticasGastosDTO {
    private BigDecimal totalGastado;
    private Long cantidadGastos;
    private BigDecimal promedioGasto;
    private Map<String, BigDecimal> gastosPorCategoria;
    private Map<String, BigDecimal> gastosPorMetodoPago;
}