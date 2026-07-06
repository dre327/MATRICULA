package com.example.MATRICULA.app.seguridad.auth.facade.impl;

import com.example.MATRICULA.app.seguridad.auth.facade.AuthFacade;
import com.example.MATRICULA.app.seguridad.auth.service.AuthService;
import com.example.MATRICULA.app.seguridad.entity.Usuario;
import com.example.MATRICULA.app.seguridad.entity.vista.RolPermisoVista;
import com.example.MATRICULA.config.mapper.SeguridadMapper;
import com.example.MATRICULA.dto.usuario.LoginRequest;
import com.example.MATRICULA.dto.usuario.LoginResponse;
import com.example.MATRICULA.security.CustomUserDetails;
import com.example.MATRICULA.security.SessionContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Facade que orquesta el flujo de login.
 * - Delega la autenticación al service (Spring Security AuthenticationManager).
 * - Guarda el SecurityContext en la sesión HTTP con SecurityContextRepository
 *   (requerido a partir de Spring Security 6 — antes bastaba con
 *   SecurityContextHolder.setContext).
 * - Arma el LoginResponse con usuario + permisos del rol.
 */
@Service
@RequiredArgsConstructor
public class AuthFacadeImpl implements AuthFacade {

    private final AuthService authService;
    private final SessionContext sessionContext;
    private final SeguridadMapper seguridadMapper;

    /**
     * Repository que sabe cómo persistir el SecurityContext en la HttpSession
     * (bajo el atributo SPRING_SECURITY_CONTEXT). Sin esto, el context se
     * pierde al terminar el request y el usuario no queda logueado.
     */
    private final SecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();

    @Override
    public LoginResponse login(LoginRequest req, HttpServletRequest httpReq, HttpServletResponse httpRes) {
        // 1. Autenticar
        Authentication auth = authService.autenticar(req.getUsername(), req.getPassword());

        // 2. Guardar SecurityContext en la sesión
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, httpReq, httpRes);

        // 3. Armar LoginResponse
        CustomUserDetails principal = (CustomUserDetails) auth.getPrincipal();
        return armarResponse(principal.getIdUsuario(), principal.getIdRol());
    }

    @Override
    public LoginResponse me() {
        return armarResponse(sessionContext.idUsuario(), sessionContext.idRol());
    }

    private LoginResponse armarResponse(Integer idUsuario, Integer idRol) {
        Usuario usuario = authService.cargarUsuario(idUsuario);
        List<RolPermisoVista> permisos = authService.cargarPermisos(idRol);

        return LoginResponse.builder()
                .usuario(seguridadMapper.toResponse(usuario))
                .permisos(seguridadMapper.toPermisoResponseList(permisos))
                .build();
    }
}
