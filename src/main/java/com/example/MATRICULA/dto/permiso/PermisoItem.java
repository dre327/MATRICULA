package com.example.MATRICULA.dto.permiso;

import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Una fila de la grilla de permisos: qué puede hacer un rol sobre una funcionalidad.
 * Se usa dentro de {@link AplicarPermisosRequest} para enviar todos los permisos
 * de un rol en una sola operación.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermisoItem {

    @NotNull
    private Integer idFuncionalidad;

    @NotNull
    private Boolean puedeVer;

    @NotNull
    private Boolean puedeInsertar;

    @NotNull
    private Boolean puedeEditar;

    @NotNull
    private Boolean puedeEliminar;

    @NotNull
    private Boolean puedeImprimir;
}
