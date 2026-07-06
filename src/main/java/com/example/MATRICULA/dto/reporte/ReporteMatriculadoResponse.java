package com.example.MATRICULA.dto.reporte;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteMatriculadoResponse {

    private String alumno;
    private String documento;
    private String nivel;
    private Integer grado;
    private String seccion;
    private LocalDate fecha;
}
