package com.example.MATRICULA.common.exception;

import jakarta.persistence.OptimisticLockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Recurso no encontrado → 404
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleNoEncontrado(RecursoNoEncontradoException ex) {
        return build(HttpStatus.NOT_FOUND, "NO_ENCONTRADO", ex.getMessage());
    }

    // Regla de negocio violada → 400
    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Map<String, Object>> handleNegocio(NegocioException ex) {
        return build(HttpStatus.BAD_REQUEST, "NEGOCIO", ex.getMessage());
    }

    // Credenciales incorrectas en login → 401
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentials(BadCredentialsException ex) {
        return build(HttpStatus.UNAUTHORIZED, "CREDENCIALES_INVALIDAS",
                "Usuario o contraseña incorrectos");
    }

    // Cualquier otra falla de autenticación → 401
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> handleAuth(AuthenticationException ex) {
        return build(HttpStatus.UNAUTHORIZED, "NO_AUTENTICADO",
                "Debe iniciar sesión para acceder a este recurso");
    }

    // Sin permiso para la operación → 403
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException ex) {
        return build(HttpStatus.FORBIDDEN, "SIN_PERMISO",
                "No tiene permiso para realizar esta operación");
    }

    // Optimistic lock (@Version) → 409
    @ExceptionHandler({
            ObjectOptimisticLockingFailureException.class,
            OptimisticLockException.class
    })
    public ResponseEntity<Map<String, Object>> handleOptimisticLock(Exception ex) {
        return build(HttpStatus.CONFLICT, "CONFLICTO_VERSION",
                "Este registro fue modificado por otro usuario. " +
                        "Por favor recargue la pantalla e intente nuevamente.");
    }

    // Validaciones de @Valid en DTOs → 400 con lista de campos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidacion(MethodArgumentNotValidException ex) {
        Map<String, String> campos = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fe ->
                campos.put(fe.getField(), fe.getDefaultMessage()));

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("error", "VALIDACION");
        body.put("message", "Los datos enviados no son válidos");
        body.put("campos", campos);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // Cualquier otra excepción → 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenerico(Exception ex) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNO",
                "Ocurrió un error inesperado. Contacte al administrador.");
    }

    private ResponseEntity<Map<String, Object>> build(HttpStatus status, String error, String mensaje) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("error", error);
        body.put("message", mensaje);
        return ResponseEntity.status(status).body(body);
    }
}
