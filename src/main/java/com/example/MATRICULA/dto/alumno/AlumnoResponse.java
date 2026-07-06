package com.example.MATRICULA.dto.alumno;

import com.example.MATRICULA.dto.tipoDocumento.TipoDocumentoResponse;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlumnoResponse {

    private Integer idAlumno;
    private TipoDocumentoResponse tipoDocumento;
    private String nroDocumento;
    private String apPaterno;
    private String apMaterno;
    private String nombre;
    private LocalDate fecNacimiento;
    private String sexo;
    private Boolean estado;

    /** Campo derivado: "APELLIDOS, NOMBRE" — útil para grillas. */
    private String nombreCompleto;
}
