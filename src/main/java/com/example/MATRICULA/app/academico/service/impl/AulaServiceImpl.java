package com.example.MATRICULA.app.academico.service.impl;

import com.example.MATRICULA.app.academico.entity.AnioAcademico;
import com.example.MATRICULA.app.academico.entity.Aula;
import com.example.MATRICULA.app.academico.entity.Nivel;
import com.example.MATRICULA.app.academico.entity.vista.AulaCupoVista;
import com.example.MATRICULA.app.academico.repository.AulaRepository;
import com.example.MATRICULA.app.academico.repository.vista.AulaCupoVistaRepository;
import com.example.MATRICULA.app.academico.service.AnioAcademicoService;
import com.example.MATRICULA.app.academico.service.AulaService;
import com.example.MATRICULA.app.academico.service.NivelService;
import com.example.MATRICULA.common.Constantes;
import com.example.MATRICULA.common.exception.NegocioException;
import com.example.MATRICULA.common.exception.RecursoNoEncontradoException;
import com.example.MATRICULA.common.service.BaseServiceImpl;
import com.example.MATRICULA.dto.aula.AulaCreateRequest;
import com.example.MATRICULA.dto.aula.AulaUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AulaServiceImpl extends BaseServiceImpl implements AulaService {

    private final AulaRepository repo;
    private final AulaCupoVistaRepository cupoRepo;
    private final AnioAcademicoService anioService;
    private final NivelService nivelService;

    @Override
    @Transactional(readOnly = true)
    public List<Aula> listarPorAnio(Integer idAnio) {
        return repo.findByAnioAcademicoIdAnioAndEstadoTrueOrderByNivelIdNivelAscGradoAscSeccionAsc(idAnio);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AulaCupoVista> listarCuposPorAnio(Integer idAnio) {
        return cupoRepo.findByIdAnioOrderByIdNivelAscGradoAscSeccionAsc(idAnio);
    }

    @Override
    @Transactional(readOnly = true)
    public Aula obtener(Integer idAula) {
        return repo.findById(idAula)
                .orElseThrow(() -> new RecursoNoEncontradoException("Aula", idAula));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Aula crear(AulaCreateRequest req) {
        if (repo.existsByAnioAcademicoIdAnioAndNivelIdNivelAndGradoAndSeccion(
                req.getIdAnio(), req.getIdNivel(), req.getGrado(), req.getSeccion()))
            throw new NegocioException("Ya existe un aula con esas características");

        AnioAcademico anio = anioService.obtener(req.getIdAnio());
        Nivel nivel = nivelService.obtener(req.getIdNivel());

        Aula nuevo = Aula.builder()
                .anioAcademico(anio)
                .nivel(nivel)
                .grado(req.getGrado())
                .seccion(req.getSeccion().trim().toUpperCase())
                .estado(Boolean.TRUE)
                .usuInsert(session.username())
                .build();
        Aula guardado = repo.save(nuevo);

        auditar(Constantes.MODULO_AULAS, "aula", Constantes.OP_INSERT,
                guardado.getIdAula(), null, snapshot(guardado));
        return guardado;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Aula actualizar(Integer idAula, AulaUpdateRequest req) {
        Aula existe = obtener(idAula);

        // Optimistic lock — verificamos antes de tocar la entidad
        if (!existe.getVersion().equals(req.getVersion()))
            throw new ObjectOptimisticLockingFailureException(Aula.class, idAula);

        Object antes = snapshot(existe);
        Nivel nivel = nivelService.obtener(req.getIdNivel());

        existe.setNivel(nivel);
        existe.setGrado(req.getGrado());
        existe.setSeccion(req.getSeccion().trim().toUpperCase());
        existe.setUsuUpdate(session.username());
        Aula guardado = repo.save(existe);

        auditar(Constantes.MODULO_AULAS, "aula", Constantes.OP_UPDATE,
                idAula, antes, snapshot(guardado));
        return guardado;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void eliminar(Integer idAula) {
        Aula existe = obtener(idAula);
        Object antes = snapshot(existe);
        existe.setEstado(Boolean.FALSE);
        existe.setUsuUpdate(session.username());
        repo.save(existe);

        auditar(Constantes.MODULO_AULAS, "aula", Constantes.OP_DELETE,
                idAula, antes, null);
    }

    private Map<String, Object> snapshot(Aula a) {
        Map<String, Object> m = new java.util.LinkedHashMap<>();
        m.put("idAula",   a.getIdAula());
        m.put("idAnio",   a.getAnioAcademico() != null ? a.getAnioAcademico().getIdAnio() : null);
        m.put("idNivel",  a.getNivel() != null ? a.getNivel().getIdNivel() : null);
        m.put("grado",    a.getGrado());
        m.put("seccion",  a.getSeccion());
        m.put("estado",   a.getEstado());
        m.put("version",  a.getVersion());
        return m;
    }
}
