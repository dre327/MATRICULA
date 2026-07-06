package com.example.MATRICULA.dto.aula;

import lombok.*;

/**
 * Vista de cupos por aula (desde vw_aula_cupo).
 * Se muestra en el modal de selección de aula al matricular.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AulaCupoResponse {

    private Integer idAula;
    private Integer idAnio;
    private Integer idNivel;
    private String nivel;
    private Integer grado;
    private String seccion;
    private Integer capacidad;
    private Integer ocupados;

    /** Cupos disponibles = capacidad - ocupados. */
    private Integer disponibles;
}
