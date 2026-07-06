package com.example.MATRICULA.common.service;

import com.example.MATRICULA.app.auditoria.service.AuditoriaService;
import com.example.MATRICULA.security.SessionContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Base para todos los ServiceImpl. Provee acceso a:
 *   - session:          info del usuario autenticado (username, idRol, ip, etc.)
 *   - auditoriaService: para registrar operaciones en la bitácora
 *   - auditar(...):     atajo que delega a auditoriaService.registrar
 *
 * Se usa inyección por setter para que los ServiceImpl hijos puedan seguir
 * usando @RequiredArgsConstructor con sus propios repositorios sin lidiar
 * con constructores super().
 *
 * Uso típico en un hijo:
 *
 *   @Service
 *   @RequiredArgsConstructor
 *   public class UsuarioServiceImpl extends BaseServiceImpl implements UsuarioService {
 *       private final UsuarioRepository repo;
 *
 *       @Transactional
 *       public Usuario crear(Usuario nuevo) {
 *           nuevo.setUsuInsert(session.username());
 *           Usuario guardado = repo.save(nuevo);
 *           auditar(MODULO_USUARIOS, "usuario", OP_INSERT,
 *                   guardado.getIdUsuario(), null, guardado);
 *           return guardado;
 *       }
 *   }
 */
public abstract class BaseServiceImpl {

    protected SessionContext session;
    protected AuditoriaService auditoriaService;

    @Autowired
    public void setSession(SessionContext session) {
        this.session = session;
    }

    @Autowired
    public void setAuditoriaService(AuditoriaService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }

    /**
     * Atajo para registrar una entrada en la bitácora.
     * Debe llamarse dentro de la transacción del proceso auditado.
     */
    protected void auditar(String modulo, String tabla, String operacion,
                           Integer idRegistro, Object antes, Object despues) {
        auditoriaService.registrar(modulo, tabla, operacion, idRegistro, antes, despues);
    }
}
