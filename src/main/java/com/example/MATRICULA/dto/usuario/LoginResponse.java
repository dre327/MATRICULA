package com.example.MATRICULA.dto.usuario;

import com.example.MATRICULA.dto.permiso.PermisoResponse;
import lombok.*;

import java.util.List;

/**
 * Response del login. Trae el usuario autenticado y los permisos que
 * su rol tiene sobre el sistema (para renderizar el menú del frontend).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

    private UsuarioResponse usuario;
    private List<PermisoResponse> permisos;
}
