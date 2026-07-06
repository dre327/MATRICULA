package com.example.MATRICULA.app.seguridad.service.impl;

import com.example.MATRICULA.app.seguridad.entity.Rol;
import com.example.MATRICULA.app.seguridad.entity.Usuario;
import com.example.MATRICULA.app.seguridad.repository.RolRepository;
import com.example.MATRICULA.app.seguridad.repository.UsuarioRepository;
import com.example.MATRICULA.app.seguridad.service.UsuarioService;
import com.example.MATRICULA.common.Constantes;
import com.example.MATRICULA.common.exception.NegocioException;
import com.example.MATRICULA.common.exception.RecursoNoEncontradoException;
import com.example.MATRICULA.common.service.BaseServiceImpl;
import com.example.MATRICULA.dto.usuario.UsuarioCreateRequest;
import com.example.MATRICULA.dto.usuario.UsuarioUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl extends BaseServiceImpl implements UsuarioService {

    private final UsuarioRepository repo;
    private final RolRepository rolRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarActivos() {
        return repo.findByEstadoTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario obtener(Integer idUsuario) {
        return repo.findById(idUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario", idUsuario));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Usuario crear(UsuarioCreateRequest req) {
        if (repo.existsByUsername(req.getUsername()))
            throw new NegocioException("El username ya existe");

        Rol rol = rolRepo.findById(req.getIdRol())
                .orElseThrow(() -> new RecursoNoEncontradoException("Rol", req.getIdRol()));

        Usuario nuevo = Usuario.builder()
                .username(req.getUsername().trim())
                .passwordHash(passwordEncoder.encode(req.getPassword()))
                .nombreCompleto(req.getNombreCompleto().trim())
                .rol(rol)
                .estado(Boolean.TRUE)
                .usuInsert(session.username())
                .build();
        Usuario guardado = repo.save(nuevo);

        auditar(Constantes.MODULO_USUARIOS, "usuario", Constantes.OP_INSERT,
                guardado.getIdUsuario(), null, snapshot(guardado));
        return guardado;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Usuario actualizar(Integer idUsuario, UsuarioUpdateRequest req) {
        Usuario existe = obtener(idUsuario);

        Rol rol = rolRepo.findById(req.getIdRol())
                .orElseThrow(() -> new RecursoNoEncontradoException("Rol", req.getIdRol()));

        Object antes = snapshot(existe);
        existe.setNombreCompleto(req.getNombreCompleto().trim());
        existe.setRol(rol);
        existe.setUsuUpdate(session.username());
        Usuario guardado = repo.save(existe);

        auditar(Constantes.MODULO_USUARIOS, "usuario", Constantes.OP_UPDATE,
                idUsuario, antes, snapshot(guardado));
        return guardado;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cambiarPassword(Integer idUsuario, String passwordActual, String passwordNueva) {
        Usuario existe = obtener(idUsuario);

        if (!passwordEncoder.matches(passwordActual, existe.getPasswordHash()))
            throw new NegocioException("La contraseña actual es incorrecta");

        existe.setPasswordHash(passwordEncoder.encode(passwordNueva));
        existe.setUsuUpdate(session.username());
        repo.save(existe);

        // No incluimos el hash en la auditoría por seguridad.
        auditar(Constantes.MODULO_USUARIOS, "usuario", Constantes.OP_CAMBIO_PASSWORD,
                idUsuario, null, Map.of("idUsuario", idUsuario, "cambiadoPor", session.username()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void eliminar(Integer idUsuario) {
        Usuario existe = obtener(idUsuario);

        if (Constantes.ROL_SUPERUSUARIO.equalsIgnoreCase(existe.getRol().getNombre()))
            throw new NegocioException("No se puede eliminar un usuario Superusuario");

        Object antes = snapshot(existe);
        existe.setEstado(Boolean.FALSE);
        existe.setUsuUpdate(session.username());
        repo.save(existe);

        auditar(Constantes.MODULO_USUARIOS, "usuario", Constantes.OP_DELETE,
                idUsuario, antes, null);
    }

    /** Snapshot sin passwordHash para auditoría. */
    private Map<String, Object> snapshot(Usuario u) {
        return Map.of(
                "idUsuario",      u.getIdUsuario(),
                "username",       u.getUsername(),
                "nombreCompleto", u.getNombreCompleto(),
                "idRol",          u.getRol() != null ? u.getRol().getIdRol() : null,
                "estado",         u.getEstado());
    }
}
