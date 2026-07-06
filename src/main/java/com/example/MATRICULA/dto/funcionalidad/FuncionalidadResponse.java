package com.example.MATRICULA.dto.funcionalidad;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FuncionalidadResponse {

    private Integer idFuncionalidad;
    private String nombre;
    private String url;
    private String icono;
    private Integer idPadre;
    private Integer orden;
    private Boolean estado;
}
