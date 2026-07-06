package com.example.MATRICULA.dto.tipoConcepto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoConceptoResponse {

    private Integer idTipoConc;
    private String descripcion;
    private Boolean estado;
    /** Si true, al crear conceptos con este tipo se generan N cuotas por meses. */
    private Boolean esRecurrente;
    private Integer cantidadCuotas;
}
