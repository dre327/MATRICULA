package com.example.MATRICULA.dto.auditoria;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Filtro para consulta paginada de la bitácora de auditoría.
 * Todos los campos son opcionales — se aplican como Specifications inline en el service.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditoriaFiltroRequest {

    private String usuario;
    private String modulo;
    private String tabla;
    private String operacion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    @Min(0)
    @Builder.Default
    private Integer pagina = 0;

    @Positive
    @Builder.Default
    private Integer tamano = 50;
}
