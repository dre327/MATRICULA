package com.example.MATRICULA.app.auditoria.service;

/**
 * Servicio transversal para registrar operaciones en la bitácora de auditoría.
 *
 * Debe llamarse SIEMPRE dentro de la transacción del proceso que audita.
 * Si la transacción hace rollback, la fila de auditoría también se revierte.
 *
 * IMPORTANTE: los parámetros valorAnterior/valorNuevo se serializan a JSON
 * con Jackson. Pasar DTOs o Maps limpios — NUNCA entidades JPA con relaciones
 * lazy sin inicializar (Jackson intentaría cargarlas y podría fallar).
 */
public interface AuditoriaService {

    /**
     * @param modulo         Constantes.MODULO_* — el módulo de negocio
     * @param tabla          nombre físico de la tabla afectada (ej. "usuario")
     * @param operacion      Constantes.OP_* — INSERT/UPDATE/DELETE/LOGIN/PAGO/...
     * @param idRegistro     PK del registro afectado (puede ser null para operaciones globales)
     * @param valorAnterior  snapshot antes del cambio; null en INSERT
     * @param valorNuevo     snapshot después del cambio; null en DELETE
     */
    void registrar(String modulo, String tabla, String operacion,
                   Integer idRegistro, Object valorAnterior, Object valorNuevo);
}
