package com.arthur.finanzas.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDTO {
    private Long id;
    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 100, message = "La descripción no puede exceder los 100 caracteres")
    private String description;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser un número positivo")
    private BigDecimal amount;

    @NotNull(message = "La fecha es obligatoria")
    @PastOrPresent(message = "La fecha no puede ser futura") // <-- Validación de fecha
    private LocalDateTime date;

    @NotBlank(message = "El tipo es obligatorio")
    @Pattern(regexp = "INGRESO|GASTO", message = "El tipo debe ser 'INGRESO' o 'GASTO'") // <-- Validación de tipo
    private String type;
}