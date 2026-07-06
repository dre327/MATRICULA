package com.example.MATRICULA.dto.concepto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConceptoUpdateRequest {

    @NotBlank
    @Size(max = 100)
    private String descripcion;

    @NotNull
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a cero")
    @Digits(integer = 8, fraction = 2)
    private BigDecimal monto;

    @NotNull
    @Positive
    private Integer orden;

    @NotNull
    private Boolean esObligatorio;

    /** Version requerido para optimistic lock (ejemplo del documento). */
    @NotNull
    private Long version;
}
