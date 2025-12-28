package com.arthur.finanzas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data // Genera getters, setters, toString, equals y hashcode automáticamente
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Column(nullable = false)
    private String description;

    @Positive(message = "El monto debe ser mayor a cero")
    @Column(nullable = false)
    private BigDecimal amount; // BigDecimal es obligatorio para finanzas

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private String type; // Ejemplo: "INCOME" o "EXPENSE"

    @PrePersist
    protected void onCreate() {
        if (this.date == null) {
            this.date = LocalDateTime.now();
        }
    }
}