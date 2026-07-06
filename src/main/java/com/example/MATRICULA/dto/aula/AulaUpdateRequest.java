package com.example.MATRICULA.dto.aula;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AulaUpdateRequest {

    @NotNull
    private Integer idNivel;

    @NotNull
    @Positive
    private Integer grado;

    @NotBlank
    @Size(max = 5)
    @Pattern(regexp = "^[A-Z]+$", message = "Sólo letras mayúsculas")
    private String seccion;

    /** Version requerido para optimistic lock. */
    @NotNull
    private Long version;
}
