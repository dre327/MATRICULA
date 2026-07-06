package com.example.MATRICULA.app.auditoria.service.impl;

import com.example.MATRICULA.app.auditoria.entity.Auditoria;
import com.example.MATRICULA.app.auditoria.repository.AuditoriaRepository;
import com.example.MATRICULA.app.auditoria.service.AuditoriaService;
import com.example.MATRICULA.app.seguridad.entity.Usuario;
import com.example.MATRICULA.app.seguridad.repository.UsuarioRepository;
import com.example.MATRICULA.security.SessionContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditoriaServiceImpl implements AuditoriaService {

    private final AuditoriaRepository auditoriaRepo;
    private final UsuarioRepository usuarioRepo;
    private final SessionContext session;
    private final ObjectMapper objectMapper;

    /**
     * Participa en la transacción del caller (Propagation.REQUIRED por default),
     * así el rollback también revierte esta fila si el proceso falla.
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void registrar(String modulo, String tabla, String operacion,
                          Integer idRegistro, Object valorAnterior, Object valorNuevo) {
        try {
            Usuario usuario = usuarioRepo.getReferenceById(session.idUsuario());

            Auditoria a = Auditoria.builder()
                    .usuario(usuario)
                    .modulo(modulo)
                    .tablaAfectada(tabla)
                    .operacion(operacion)
                    .codigoRegistro(idRegistro)
                    .valorAnterior(safeSerialize(valorAnterior))
                    .valorNuevo(safeSerialize(valorNuevo))
                    .ipOrigen(session.ipCliente())
                    .navegador(session.userAgent())
                    .build();

            auditoriaRepo.save(a);
        } catch (Exception e) {
            // La auditoría no debería reventar el proceso principal. Se loguea el error
            // pero no se propaga la excepción — el negocio ya se completó exitosamente.
            log.error("Error registrando auditoría [modulo={}, op={}]: {}",
                    modulo, operacion, e.getMessage(), e);
        }
    }

    private String safeSerialize(Object o) {
        if (o == null) return null;
        try {
            return objectMapper.writeValueAsString(o);
        } catch (Exception e) {
            log.warn("No se pudo serializar objeto para auditoría: {}", e.getMessage());
            return "{\"_error\":\"serialization failed\"}";
        }
    }
}
