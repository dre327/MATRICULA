package com.example.MATRICULA.security;

import com.example.MATRICULA.common.exception.NegocioException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Fachada única para leer información del usuario autenticado.
 * Cualquier service que necesite saber "quién está haciendo esto" lo inyecta.
 *
 * Uso típico:
 *   private final SessionContext session;
 *   ...
 *   nuevo.setUsuInsert(session.username());
 */
@Component
@RequiredArgsConstructor
public class SessionContext {

    /**
     * Nombre de usuario autenticado. Nunca null si se llama desde
     * un request autenticado (los públicos no llegan hasta el service).
     */
    public String username() {
        return userDetails().getUsername();
    }

    public Integer idUsuario() {
        return userDetails().getIdUsuario();
    }

    public Integer idRol() {
        return userDetails().getIdRol();
    }

    public String nombreRol() {
        return userDetails().getNombreRol();
    }

    /** IP desde la que llega el request — se usa para auditoría. */
    public String ipCliente() {
        HttpServletRequest req = request();
        if (req == null) return null;
        String xf = req.getHeader("X-Forwarded-For");
        return (xf != null && !xf.isBlank()) ? xf.split(",")[0].trim() : req.getRemoteAddr();
    }

    /** User agent — se usa para auditoría. */
    public String userAgent() {
        HttpServletRequest req = request();
        return req != null ? req.getHeader("User-Agent") : null;
    }

    // ── internos ─────────────────────────────────────────────────

    private CustomUserDetails userDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()
                || !(auth.getPrincipal() instanceof CustomUserDetails cud))
            throw new NegocioException("No hay usuario autenticado en la sesión");
        return cud;
    }

    private HttpServletRequest request() {
        if (RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes attrs)
            return attrs.getRequest();
        return null;
    }
}
