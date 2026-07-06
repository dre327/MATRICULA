package com.example.MATRICULA.dto.aula;

import com.example.MATRICULA.dto.anio.AnioAcademicoResponse;
import com.example.MATRICULA.dto.nivel.NivelResponse;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AulaResponse {

    private Integer idAula;
    private AnioAcademicoResponse anioAcademico;
    private NivelResponse nivel;
    private Integer grado;
    private String seccion;
    private Boolean estado;
    private Long version;
}
