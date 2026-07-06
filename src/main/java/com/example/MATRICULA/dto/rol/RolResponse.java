package com.example.MATRICULA.dto.rol;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolResponse {

    private Integer idRol;
    private String nombre;
    private String descripcion;
    private Boolean estado;
}
