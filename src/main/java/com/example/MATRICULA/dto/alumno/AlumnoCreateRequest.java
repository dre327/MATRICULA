package com.example.MATRICULA.dto.alumno;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlumnoCreateRequest {

    @NotNull
    private Integer idTipoDocumento;

    @NotBlank
    @Size(max = 20)
    @Pattern(regexp = "^[A-Za-z0-9-]+$",
             message = "Solo letras, números y guión")
    private String nroDocumento;

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

    /** M = Masculino, F = Femenino */
    @Pattern(regexp = "[MF]", message = "Debe ser 'M' o 'F'")
    private String sexo;
}
