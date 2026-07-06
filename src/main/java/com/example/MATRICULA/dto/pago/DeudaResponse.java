package com.example.MATRICULA.dto.pago;

import lombok.*;

import java.math.BigDecimal;

/**
 * Fila de deuda mostrada al buscar cuotas pendientes de un alumno en un año.
 * Ordenada por Concepto.orden ascendente — el frontend debe pagar en orden.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeudaResponse {

    private Integer idCuota;
    private Integer orden;
    private String concepto;
    private BigDecimal monto;

    /** P = Pendiente, C = Cancelado */
    private String estadoPago;

    /** true si esta cuota se puede pagar ya (no hay pendientes con orden menor). */
    private Boolean pagable;
}
