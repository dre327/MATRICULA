package com.example.MATRICULA.app.seguridad.service.impl;

import com.example.MATRICULA.app.seguridad.entity.Rol;
import com.example.MATRICULA.app.seguridad.repository.RolRepository;
import com.example.MATRICULA.app.seguridad.service.RolService;
import com.example.MATRICULA.common.Constantes;
import com.example.MATRICULA.common.exception.NegocioException;
import com.example.MATRICULA.common.exception.RecursoNoEncontradoException;
import com.example.MATRICULA.common.service.BaseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RolServiceImpl extends BaseServiceImpl implements RolService {

    private final RolRepository repo;

    @Override
    @Transactional(readOnly = true)
    public List<Rol> listarActivos() {
        return repo.findByEstadoTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public Rol obtener(Integer idRol) {
        return repo.findById(idRol)
                .orElseThrow(() -> new RecursoNoEncontradoException("Rol", idRol));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Rol crear(String nombre, String descripcion) {
        if (repo.existsByNombreIgnoreCase(nombre))
            throw new NegocioException("Ya existe un rol con ese nombre");

        Rol nuevo = Rol.builder()
                .nombre(nombre.trim().toUpperCase())
                .descripcion(descripcion)
                .estado(Boolean.TRUE)
                .build();
        Rol guardado = repo.save(nuevo);

        auditar(Constantes.MODULO_USUARIOS, "rol", Constantes.OP_INSERT,
                guardado.getIdRol(), null, snapshot(guardado));
        return guardado;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Rol actualizar(Integer idRol, String nombre, String descripcion) {
        Rol existe = obtener(idRol);

        if (Constantes.ROL_SUPERUSUARIO.equalsIgnoreCase(existe.getNombre())
                && !Constantes.ROL_SUPERUSUARIO.equalsIgnoreCase(nombre))
            throw new NegocioException("No se puede renombrar el rol SUPERUSUARIO");

        Object antes = snapshot(existe);
        existe.setNombre(nombre.trim().toUpperCase());
        existe.setDescripcion(descripcion);
        Rol guardado = repo.save(existe);

        auditar(Constantes.MODULO_USUARIOS, "rol", Constantes.OP_UPDATE,
                idRol, antes, snapshot(guardado));
        return guardado;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void eliminar(Integer idRol) {
        Rol existe = obtener(idRol);

        if (Constantes.ROL_SUPERUSUARIO.equalsIgnoreCase(existe.getNombre()))
            throw new NegocioException("No se puede eliminar el rol SUPERUSUARIO");

        Object antes = snapshot(existe);
        existe.setEstado(Boolean.FALSE);
        repo.save(existe);

        auditar(Constantes.MODULO_USUARIOS, "rol", Constantes.OP_DELETE,
                idRol, antes, null);
    }

    /** Snapshot simple del rol para la auditoría (evita lazy loading). */
    private java.util.Map<String, Object> snapshot(Rol r) {
        return java.util.Map.of(
                "idRol",       r.getIdRol(),
                "nombre",      r.getNombre(),
                "descripcion", r.getDescripcion() == null ? "" : r.getDescripcion(),
                "estado",      r.getEstado());
    }
}
