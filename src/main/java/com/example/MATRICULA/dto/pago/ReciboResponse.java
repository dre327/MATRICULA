package com.example.MATRICULA.dto.pago;

import com.example.MATRICULA.dto.cuota.CuotaResponse;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReciboResponse {

    private Integer idRecibo;
    private CuotaResponse cuota;
    private String nroBoleta;
    private BigDecimal montoPagado;
    private LocalDate fecPago;
    private String observacion;
    private Boolean estado;
}
