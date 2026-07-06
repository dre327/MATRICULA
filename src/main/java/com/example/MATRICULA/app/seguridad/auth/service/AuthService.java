package com.example.MATRICULA.app.seguridad.auth.service;

import com.example.MATRICULA.app.seguridad.entity.Usuario;
import com.example.MATRICULA.app.seguridad.entity.vista.RolPermisoVista;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AuthService {

    /**
     * Autentica usando Spring Security. Lanza BadCredentialsException si
     * el usuario/password no coincide (mapeado a 401 por GlobalExceptionHandler).
     */
    Authentication autenticar(String username, String password);

    Usuario cargarUsuario(Integer idUsuario);

    List<RolPermisoVista> cargarPermisos(Integer idRol);
}
