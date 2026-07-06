package com.example.MATRICULA.dto.permiso;

import lombok.*;

/**
 * Vista aplanada de un permiso (rol + funcionalidad + flags).
 * Se construye desde vw_rol_permiso — trae también el padre para armar el árbol
 * del menú en el frontend.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermisoResponse {

    private Integer idFuncionalidad;
    private String funcionalidad;
    private String url;
    private String icono;
    private Integer idPadre;
    private String padre;
    private Integer orden;
    private Boolean puedeVer;
    private Boolean puedeInsertar;
    private Boolean puedeEditar;
    private Boolean puedeEliminar;
    private Boolean puedeImprimir;
}
