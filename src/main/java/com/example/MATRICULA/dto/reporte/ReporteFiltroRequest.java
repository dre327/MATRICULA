package com.example.MATRICULA.dto.reporte;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

/**
 * Filtro común para los 3 reportes (matriculados, pagos, deudas).
 * El idAnio es obligatorio; los demás campos son opcionales.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteFiltroRequest {

    @NotNull
    private Integer idAnio;

    private Integer idAlumno;
    private Integer idNivel;
    private Integer idAula;

    @Min(0)
    @Builder.Default
    private Integer pagina = 0;

    @Positive
    @Builder.Default
    private Integer tamano = 50;
}
