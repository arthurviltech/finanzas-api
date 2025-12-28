package com.arthur.finanzas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GastoDTO {
    private Long id;
    private String concepto;
    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser un número positivo")
    private BigDecimal monto;
    private String categoria;
    @NotNull(message = "La fecha es obligatoria")
    private String fecha;
    private String metodoPago;
    private String notas;
    private LocalDateTime fechaCreacion;
}