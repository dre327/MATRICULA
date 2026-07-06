package com.example.MATRICULA.dto.anio;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnioAcademicoRequest {

    @NotNull
    @Min(2000)
    @Max(2100)
    private Integer anio;

    @Size(max = 100)
    private String descripcion;
}
