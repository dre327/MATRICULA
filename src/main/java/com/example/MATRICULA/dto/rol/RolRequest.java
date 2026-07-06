package com.example.MATRICULA.dto.rol;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolRequest {

    @NotBlank
    @Size(max = 50)
    private String nombre;

    @Size(max = 150)
    private String descripcion;
}
