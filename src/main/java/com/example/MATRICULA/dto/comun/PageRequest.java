package com.example.MATRICULA.dto.comun;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.*;

/**
 * Request base de paginación.
 * - pagina: índice 0-based (página 0 = primeros N registros).
 * - tamano: cantidad de registros por página.
 * Los defaults se aplican con @Builder.Default y valores de campo,
 * que Jackson respeta cuando el JSON no incluye la propiedad.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageRequest {

    @Min(0)
    @Builder.Default
    private Integer pagina = 0;

    @Positive
    @Builder.Default
    private Integer tamano = 20;
}
