package com.example.MATRICULA.dto.reporte;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportePagoResponse {

    private String boleta;
    private String alumno;
    private String documento;
    private String concepto;
    private BigDecimal monto;
    private LocalDate fecha;
}
