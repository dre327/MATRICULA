package com.example.MATRICULA.dto.alumno;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.*;

/**
 * Filtro para búsqueda de alumnos con paginación.
 * Todos los campos son opcionales — se aplican como Specifications inline en el service.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlumnoFiltroRequest {

    private String nombre;
    private String apellido;
    private String nroDocumento;
    private Integer idTipoDocumento;

    @Min(0)
    @Builder.Default
    private Integer pagina = 0;

    @Positive
    @Builder.Default
    private Integer tamano = 20;
}
