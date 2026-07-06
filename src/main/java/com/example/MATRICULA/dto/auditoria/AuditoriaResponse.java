package com.example.MATRICULA.dto.auditoria;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditoriaResponse {

    private Long id;
    private LocalDateTime fechaHora;
    private String usuario;
    private String nombreCompleto;
    private String rol;
    private String modulo;
    private String tablaAfectada;
    private String operacion;
    private Integer codigoRegistro;
    private String ipOrigen;
    private String equipo;
    private String navegador;
}
