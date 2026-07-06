package com.example.MATRICULA.dto.concepto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConceptoCreateRequest {

    @NotNull
    private Integer idAnio;

    @NotNull
    private Integer idTipoConcepto;

    /**
     * Opcional en tipos recurrentes (MENSUALIDAD) — el backend arma los
     * nombres automáticamente (MENSUALIDAD MARZO, ABRIL, …).
     * Obligatorio en tipos únicos (MATRICULA, MATERIAL, UNIFORME) — el service
     * lo valida ahí porque necesita saber el tipo primero.
     */
    @Size(max = 100)
    private String descripcion;

    @NotNull
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a cero")
    @Digits(integer = 8, fraction = 2)
    private BigDecimal monto;

    /**
     * Opcional al crear. Si no se envía (null), el backend auto-asigna
     * el siguiente número disponible (MAX(orden) + 1) para ese año.
     */
    @Positive
    private Integer orden;

    @NotNull
    private Boolean esObligatorio;
}
