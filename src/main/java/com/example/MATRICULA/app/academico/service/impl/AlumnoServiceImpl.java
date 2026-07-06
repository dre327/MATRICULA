package com.example.MATRICULA.app.academico.service.impl;

import com.example.MATRICULA.app.academico.entity.Alumno;
import com.example.MATRICULA.app.academico.entity.TipoDocumento;
import com.example.MATRICULA.app.academico.repository.AlumnoRepository;
import com.example.MATRICULA.app.academico.service.AlumnoService;
import com.example.MATRICULA.app.academico.service.TipoDocumentoService;
import com.example.MATRICULA.common.Constantes;
import com.example.MATRICULA.common.exception.NegocioException;
import com.example.MATRICULA.common.exception.RecursoNoEncontradoException;
import com.example.MATRICULA.common.service.BaseServiceImpl;
import com.example.MATRICULA.dto.alumno.AlumnoCreateRequest;
import com.example.MATRICULA.dto.alumno.AlumnoFiltroRequest;
import com.example.MATRICULA.dto.alumno.AlumnoUpdateRequest;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AlumnoServiceImpl extends BaseServiceImpl implements AlumnoService {

    private final AlumnoRepository repo;
    private final TipoDocumentoService tipoDocumentoService;

    /**
     * Búsqueda dinámica con Specifications inline — mismo patrón que
     * el proyecto de referencia. Todos los filtros son opcionales;
     * un filtro null se ignora simplemente no agregándolo al predicate.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Alumno> buscar(AlumnoFiltroRequest filtro) {
        return repo.findAll(
                (root, query, cb) -> {
                    List<Predicate> preds = new ArrayList<>();
                    preds.add(cb.isTrue(root.get("estado")));

                    if (filtro.getIdTipoDocumento() != null)
                        preds.add(cb.equal(
                                root.get("tipoDocumento").get("idTipoDoc"),
                                filtro.getIdTipoDocumento()));

                    if (hasText(filtro.getNroDocumento()))
                        preds.add(cb.like(
                                root.get("nroDocumento"),
                                filtro.getNroDocumento().trim() + "%"));

                    if (hasText(filtro.getNombre()))
                        preds.add(cb.like(
                                cb.upper(root.get("nombre")),
                                "%" + filtro.getNombre().trim().toUpperCase() + "%"));

                    if (hasText(filtro.getApellido())) {
                        String pat = "%" + filtro.getApellido().trim().toUpperCase() + "%";
                        preds.add(cb.or(
                                cb.like(cb.upper(root.get("apPaterno")), pat),
                                cb.like(cb.upper(root.get("apMaterno")), pat)));
                    }

                    return cb.and(preds.toArray(new Predicate[0]));
                },
                PageRequest.of(filtro.getPagina(), filtro.getTamano(),
                        Sort.by(Sort.Direction.ASC, "apPaterno", "apMaterno", "nombre"))
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Alumno obtener(Integer idAlumno) {
        return repo.findById(idAlumno)
                .orElseThrow(() -> new RecursoNoEncontradoException("Alumno", idAlumno));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Alumno crear(AlumnoCreateRequest req) {
        if (repo.existsByTipoDocumentoIdTipoDocAndNroDocumento(
                req.getIdTipoDocumento(), req.getNroDocumento()))
            throw new NegocioException("Ya existe un alumno con ese tipo y número de documento");

        TipoDocumento td = tipoDocumentoService.obtener(req.getIdTipoDocumento());

        Alumno nuevo = Alumno.builder()
                .tipoDocumento(td)
                .nroDocumento(req.getNroDocumento().trim())
                .apPaterno(req.getApPaterno().trim().toUpperCase())
                .apMaterno(req.getApMaterno().trim().toUpperCase())
                .nombre(req.getNombre().trim().toUpperCase())
                .fecNacimiento(req.getFecNacimiento())
                .sexo(req.getSexo() == null ? null : req.getSexo().charAt(0))
                .estado(Boolean.TRUE)
                .usuInsert(session.username())
                .build();
        Alumno guardado = repo.save(nuevo);

        auditar(Constantes.MODULO_ALUMNOS, "alumno", Constantes.OP_INSERT,
                guardado.getIdAlumno(), null, snapshot(guardado));
        return guardado;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Alumno actualizar(Integer idAlumno, AlumnoUpdateRequest req) {
        Alumno existe = obtener(idAlumno);
        Object antes = snapshot(existe);

        existe.setApPaterno(req.getApPaterno().trim().toUpperCase());
        existe.setApMaterno(req.getApMaterno().trim().toUpperCase());
        existe.setNombre(req.getNombre().trim().toUpperCase());
        existe.setFecNacimiento(req.getFecNacimiento());
        existe.setSexo(req.getSexo() == null ? null : req.getSexo().charAt(0));
        existe.setUsuUpdate(session.username());
        Alumno guardado = repo.save(existe);

        auditar(Constantes.MODULO_ALUMNOS, "alumno", Constantes.OP_UPDATE,
                idAlumno, antes, snapshot(guardado));
        return guardado;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void eliminar(Integer idAlumno) {
        Alumno existe = obtener(idAlumno);
        Object antes = snapshot(existe);
        existe.setEstado(Boolean.FALSE);
        existe.setUsuUpdate(session.username());
        repo.save(existe);

        auditar(Constantes.MODULO_ALUMNOS, "alumno", Constantes.OP_DELETE,
                idAlumno, antes, null);
    }

    private static boolean hasText(String s) {
        return s != null && !s.trim().isEmpty();
    }

    private Map<String, Object> snapshot(Alumno a) {
        Map<String, Object> m = new java.util.LinkedHashMap<>();
        m.put("idAlumno",     a.getIdAlumno());
        m.put("idTipoDoc",    a.getTipoDocumento() != null ? a.getTipoDocumento().getIdTipoDoc() : null);
        m.put("nroDocumento", a.getNroDocumento());
        m.put("apPaterno",    a.getApPaterno());
        m.put("apMaterno",    a.getApMaterno());
        m.put("nombre",       a.getNombre());
        m.put("fecNacimiento", a.getFecNacimiento() != null ? a.getFecNacimiento().toString() : null);
        m.put("sexo",         a.getSexo() != null ? a.getSexo().toString() : null);
        m.put("estado",       a.getEstado());
        return m;
    }
}
