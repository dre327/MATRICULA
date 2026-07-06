package com.example.MATRICULA.dto.matricula;

import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Request para matricular un alumno.
 * Dispara la transacción: crea matrícula + genera cuotas + registra auditoría.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatricularRequest {

    @NotNull
    private Integer idAlumno;

    @NotNull
    private Integer idAula;

    @NotNull
    private Integer idAnio;
}
