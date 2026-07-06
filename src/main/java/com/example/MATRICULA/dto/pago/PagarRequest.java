package com.example.MATRICULA.dto.pago;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Request para pagar una cuota específica.
 * Dispara la transacción: valida orden ordenado, marca cuota como pagada,
 * genera correlativo, crea recibo, registra auditoría.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagarRequest {

    @NotNull
    private Integer idAlumno;

    @NotNull
    private Integer idAnio;

    @NotNull
    private Integer idCuota;

    @Size(max = 200)
    private String observacion;
}
