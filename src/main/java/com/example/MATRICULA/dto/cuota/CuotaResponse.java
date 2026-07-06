package com.example.MATRICULA.dto.cuota;

import com.example.MATRICULA.dto.concepto.ConceptoResponse;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuotaResponse {

    private Integer idCuota;
    private Integer idMatricula;
    private ConceptoResponse concepto;
    private BigDecimal monto;

    /** P = Pendiente, C = Cancelado, A = Anulado */
    private String estadoPago;

    private Long version;
}
