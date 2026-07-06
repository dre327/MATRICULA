package com.example.MATRICULA.security;

import com.example.MATRICULA.app.seguridad.entity.vista.RolPermisoVista;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Aspecto que aplica el chequeo de permisos.
 * Se dispara ANTES de la ejecución de cualquier método anotado con @RequierePermiso.
 *
 * Flujo:
 *   1. Lee idRol del usuario autenticado (SessionContext).
 *   2. Obtiene los permisos del rol desde el caché.
 *   3. Busca la fila correspondiente al módulo pedido.
 *   4. Verifica el flag (puedeVer / puedeInsertar / ...).
 *   5. Si no tiene permiso → AccessDeniedException → HTTP 403.
 *
 * El aspecto no se activa en endpoints públicos (login, logout, /me, estáticos)
 * porque esos no tienen la anotación.
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class PermisoAspect {

    private final SessionContext sessionContext;
    private final PermisoCache permisoCache;

    @Before("@annotation(requierePermiso)")
    public void verificar(RequierePermiso requierePermiso) {
        Integer idRol = sessionContext.idRol();
        String moduloRequerido = requierePermiso.modulo();
        TipoAccion accion = requierePermiso.accion();

        List<RolPermisoVista> permisos = permisoCache.getPermisosDeRol(idRol);

        RolPermisoVista permiso = permisos.stream()
                .filter(p -> coincideModulo(p, moduloRequerido))
                .findFirst()
                .orElse(null);

        if (permiso == null || !tieneAccion(permiso, accion)) {
            log.warn("Acceso denegado — rol={} modulo={} accion={}",
                    sessionContext.nombreRol(), moduloRequerido, accion);
            throw new AccessDeniedException(
                    "No tiene permiso de " + accion + " sobre el módulo " + moduloRequerido);
        }
    }

    /**
     * El módulo declarado en @RequierePermiso se compara contra el nombre
     * de la funcionalidad (case-insensitive) o su URL sin la extensión .html.
     * Ejemplo: modulo="ALUMNOS" matchea funcionalidad.nombre="Alumnos"
     *          y funcionalidad.url="alumnos.html".
     */
    private boolean coincideModulo(RolPermisoVista p, String moduloRequerido) {
        if (p.getFuncionalidad() != null
                && moduloRequerido.equalsIgnoreCase(p.getFuncionalidad())) return true;
        if (p.getUrl() != null) {
            String base = p.getUrl().replaceFirst("\\.html$", "");
            if (moduloRequerido.equalsIgnoreCase(base)) return true;
        }
        return false;
    }

    private boolean tieneAccion(RolPermisoVista p, TipoAccion accion) {
        return switch (accion) {
            case VER       -> Boolean.TRUE.equals(p.getPuedeVer());
            case INSERTAR  -> Boolean.TRUE.equals(p.getPuedeInsertar());
            case EDITAR    -> Boolean.TRUE.equals(p.getPuedeEditar());
            case ELIMINAR  -> Boolean.TRUE.equals(p.getPuedeEliminar());
            case IMPRIMIR  -> Boolean.TRUE.equals(p.getPuedeImprimir());
        };
    }
}
