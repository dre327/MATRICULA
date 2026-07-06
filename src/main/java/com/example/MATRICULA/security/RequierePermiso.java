package com.example.MATRICULA.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marca un método de controller que requiere un permiso específico.
 *
 * Uso:
 *   @RequierePermiso(modulo = "ALUMNOS", accion = TipoAccion.INSERTAR)
 *   @PostMapping
 *   public AlumnoResponse crear(...) { ... }
 *
 * El módulo debe coincidir con la URL de una funcionalidad
 * (columna funcionalidad.url — ej. "alumnos.html" → módulo "ALUMNOS").
 *
 * Si el usuario autenticado no tiene el flag correspondiente, el aspecto
 * lanza AccessDeniedException → HTTP 403.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequierePermiso {

    /** Nombre del módulo tal como se registra en funcionalidad (por convención en MAYÚSCULAS). */
    String modulo();

    /** Acción requerida sobre el módulo. */
    TipoAccion accion();
}
