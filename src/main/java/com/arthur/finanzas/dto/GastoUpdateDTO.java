package com.arthur.finanzas.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GastoUpdateDTO {

    @Size(max = 200, message = "El concepto no puede exceder 200 caracteres")
    private String concepto;

    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0")
    @Digits(integer = 10, fraction = 2, message = "Formato de monto inválido")
    private BigDecimal monto;

    private String categoria;

    @PastOrPresent(message = "La fecha no puede ser futura")
    private String fecha;

    private String metodoPago;

    @Size(max = 500, message = "Las notas no pueden exceder 500 caracteres")
    private String notas;
}
