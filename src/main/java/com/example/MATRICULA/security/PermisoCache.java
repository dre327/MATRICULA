package com.example.MATRICULA.security;

import com.example.MATRICULA.app.seguridad.entity.vista.RolPermisoVista;
import com.example.MATRICULA.app.seguridad.repository.vista.RolPermisoVistaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Caché en memoria de los permisos por rol.
 * Se llena bajo demanda (la primera vez que se pide el rol) y se invalida
 * explícitamente cuando el service de permisos aplica cambios.
 *
 * Estructura interna:
 *   idRol → List<RolPermisoVista>  (todas las funcionalidades del rol)
 *
 * Es concurrent-safe (varios requests simultáneos pueden leer sin bloqueo).
 */
@Component
@RequiredArgsConstructor
public class PermisoCache {

    private final RolPermisoVistaRepository rolPermisoVistaRepo;

    private final Map<Integer, List<RolPermisoVista>> cachePorRol = new ConcurrentHashMap<>();

    /**
     * Devuelve los permisos del rol. Si no están en caché, los carga desde BD
     * y los guarda para futuras consultas.
     */
    public List<RolPermisoVista> getPermisosDeRol(Integer idRol) {
        return cachePorRol.computeIfAbsent(idRol, rolPermisoVistaRepo::findByIdRolOrderByOrden);
    }

    /**
     * Invalida la caché de un rol específico. Se llama desde el service
     * cuando cambian los permisos de ese rol.
     */
    public void invalidar(Integer idRol) {
        cachePorRol.remove(idRol);
    }

    /** Invalida toda la caché. Útil en operaciones administrativas o al arrancar. */
    public void invalidarTodo() {
        cachePorRol.clear();
    }
}
