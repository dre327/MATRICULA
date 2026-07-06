package com.example.MATRICULA.dto.permiso;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

/**
 * Se envía cuando el Superusuario hace clic en "Aplicar" en la pantalla de permisos.
 * Reemplaza todos los permisos del rol con los que vienen aquí.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AplicarPermisosRequest {

    @NotNull
    private Integer idRol;

    @NotEmpty
    @Valid
    private List<PermisoItem> permisos;
}
