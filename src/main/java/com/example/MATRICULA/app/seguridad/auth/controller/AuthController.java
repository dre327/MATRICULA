package com.example.MATRICULA.app.seguridad.auth.controller;

import com.example.MATRICULA.app.seguridad.auth.facade.AuthFacade;
import com.example.MATRICULA.dto.usuario.LoginRequest;
import com.example.MATRICULA.dto.usuario.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Endpoints de autenticación:
 * - POST /api/auth/login  → autentica y crea sesión.
 * - GET  /api/auth/me     → estado del usuario autenticado (refresh).
 *
 * El logout (POST /api/auth/logout) lo maneja la configuración de Spring Security
 * en {@link com.example.MATRICULA.security.SecurityConfig} — invalida sesión,
 * borra cookies y responde 204.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthFacade authFacade;

    @PostMapping("/login")
    public LoginResponse login(
            @Valid @RequestBody LoginRequest req,
            HttpServletRequest httpReq,
            HttpServletResponse httpRes) {
        return authFacade.login(req, httpReq, httpRes);
    }

    @GetMapping("/me")
    public LoginResponse me() {
        return authFacade.me();
    }
}
