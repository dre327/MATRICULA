package com.example.MATRICULA.dto.nivel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NivelRequest {

    @NotBlank
    @Size(max = 50)
    private String nombre;
}
