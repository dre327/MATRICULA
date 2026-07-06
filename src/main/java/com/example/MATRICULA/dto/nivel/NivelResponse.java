package com.example.MATRICULA.dto.nivel;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NivelResponse {

    private Integer idNivel;
    private String nombre;
    private Boolean estado;
}
