package com.example.MATRICULA.dto.reporte;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteDeudaResponse {

    private String alumno;
    private String documento;
    private String concepto;
    private BigDecimal monto;
    private Integer orden;
}
