package com.example.MATRICULA.dto.alumno;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlumnoUpdateRequest {

    @NotBlank
    @Size(max = 60)
    private String apPaterno;

    @NotBlank
    @Size(max = 60)
    private String apMaterno;

    @NotBlank
    @Size(max = 80)
    private String nombre;

    @Past
    private LocalDate fecNacimiento;

    @Pattern(regexp = "[MF]", message = "Debe ser 'M' o 'F'")
    private String sexo;
}
