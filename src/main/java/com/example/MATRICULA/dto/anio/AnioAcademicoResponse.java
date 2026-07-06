package com.example.MATRICULA.dto.anio;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnioAcademicoResponse {

    private Integer idAnio;
    private Integer anio;
    private String descripcion;
    private Boolean estado;
}
