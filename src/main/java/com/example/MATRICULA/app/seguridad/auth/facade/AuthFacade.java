package com.example.MATRICULA.app.seguridad.auth.facade;

import com.example.MATRICULA.dto.usuario.LoginRequest;
import com.example.MATRICULA.dto.usuario.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthFacade {

    /** Autentica y devuelve usuario + permisos. Deja la sesión creada. */
    LoginResponse login(LoginRequest req, HttpServletRequest httpReq, HttpServletResponse httpRes);

    /** Devuelve el estado del usuario actualmente autenticado (para refresh del frontend). */
    LoginResponse me();
}
