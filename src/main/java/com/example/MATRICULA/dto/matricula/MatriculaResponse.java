package com.example.MATRICULA.dto.matricula;

import com.example.MATRICULA.dto.alumno.AlumnoResponse;
import com.example.MATRICULA.dto.anio.AnioAcademicoResponse;
import com.example.MATRICULA.dto.aula.AulaResponse;
import com.example.MATRICULA.dto.cuota.CuotaResponse;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatriculaResponse {

    private Integer idMatricula;
    private AnioAcademicoResponse anioAcademico;
    private AulaResponse aula;
    private AlumnoResponse alumno;
    private LocalDate fecMatricula;
    private Boolean estado;
    private Long version;

    /** Cuotas generadas al matricular. */
    private List<CuotaResponse> cuotas;
}
