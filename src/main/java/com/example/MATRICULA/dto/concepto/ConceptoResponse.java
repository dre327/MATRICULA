package com.example.MATRICULA.dto.concepto;

import com.example.MATRICULA.dto.tipoConcepto.TipoConceptoResponse;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConceptoResponse {

    private Integer idConcepto;
    private Integer idAnio;
    private TipoConceptoResponse tipoConcepto;
    private String descripcion;
    private BigDecimal monto;
    private Integer orden;
    private Boolean esObligatorio;
    private Boolean estado;
    private Long version;
}
