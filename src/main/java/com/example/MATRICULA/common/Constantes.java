package com.example.MATRICULA.common;

/**
 * Constantes de dominio y sistema.
 * Solo van aquí valores que:
 *   - Son claves de tablas configurables (parametro, correlativo)
 *   - Son nombres de rol/módulo/operación usados en checks de código
 *
 * Los valores fijos de dominio (P/C/A, M/F) viven en enums en common/enums.
 */
public final class Constantes {

    private Constantes() {}

    // ── Meses escolares (Perú, marzo-diciembre) ──────────────────────
    /** Nombres de los 10 meses del año escolar peruano, en orden. */
    public static final String[] MESES_ESCOLARES = {
            "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO",
            "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"
    };

    // ── Nombres de tipo_concepto (para reglas de pago) ───────────────
    /** El tipo MATRICULA actúa como "gatekeeper": debe pagarse antes que cualquier otro. */
    public static final String TIPO_MATRICULA = "MATRICULA";

    // ── Claves de la tabla parametro ─────────────────────────────────
    public static final String CLAVE_MAX_ALUMNOS_AULA = "MAX_ALUMNOS_AULA";

    // ── Claves de la tabla correlativo ───────────────────────────────
    public static final String CLAVE_CORRELATIVO_BOLETA = "BOLETA";
    public static final String PREFIJO_BOLETA = "BOL-";

    // ── Nombres de rol ───────────────────────────────────────────────
    public static final String ROL_SUPERUSUARIO = "SUPERUSUARIO";
    public static final String ROL_DIRECTOR     = "DIRECTOR";
    public static final String ROL_SECRETARIA   = "SECRETARIA";

    // ── Nombres de módulo (para auditoría y @RequierePermiso) ────────
    public static final String MODULO_USUARIOS   = "USUARIOS";
    public static final String MODULO_PERMISOS   = "PERMISOS";
    public static final String MODULO_ANIOS      = "ANIOS";
    public static final String MODULO_ALUMNOS    = "ALUMNOS";
    public static final String MODULO_AULAS      = "AULAS";
    public static final String MODULO_CONCEPTOS  = "CONCEPTOS";
    public static final String MODULO_MATRICULA  = "MATRICULA";
    public static final String MODULO_PAGOS      = "PAGOS";
    public static final String MODULO_REPORTES   = "REPORTES";
    public static final String MODULO_AUDITORIA  = "AUDITORIA";

    // ── Operaciones de auditoría ─────────────────────────────────────
    public static final String OP_INSERT             = "INSERT";
    public static final String OP_UPDATE             = "UPDATE";
    public static final String OP_DELETE             = "DELETE";
    public static final String OP_LOGIN              = "LOGIN";
    public static final String OP_LOGOUT             = "LOGOUT";
    public static final String OP_MATRICULA          = "MATRICULA";
    public static final String OP_PAGO               = "PAGO";
    public static final String OP_CAMBIO_PASSWORD    = "CAMBIO_PASSWORD";
    public static final String OP_APLICAR_PERMISOS   = "APLICAR_PERMISOS";
    public static final String OP_CLONAR_CONCEPTOS   = "CLONAR_CONCEPTOS";
}
