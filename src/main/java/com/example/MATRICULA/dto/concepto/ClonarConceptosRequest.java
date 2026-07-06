package com.example.MATRICULA.dto.concepto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Clona todos los conceptos activos de idAnioOrigen hacia idAnioDestino.
 * Requerido por el documento: "herramienta para clonar conceptos de un año a otro".
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClonarConceptosRequest {

    @NotNull
    private Integer idAnioOrigen;

    @NotNull
    private Integer idAnioDestino;

    @AssertTrue(message = "El año destino debe ser distinto del origen")
    public boolean isAniosDistintos() {
        return idAnioOrigen == null || idAnioDestino == null
                || !idAnioOrigen.equals(idAnioDestino);
    }
}
