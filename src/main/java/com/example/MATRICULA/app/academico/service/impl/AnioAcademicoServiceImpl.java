package com.example.MATRICULA.app.academico.service.impl;

import com.example.MATRICULA.app.academico.entity.AnioAcademico;
import com.example.MATRICULA.app.academico.repository.AnioAcademicoRepository;
import com.example.MATRICULA.app.academico.service.AnioAcademicoService;
import com.example.MATRICULA.common.Constantes;
import com.example.MATRICULA.common.exception.NegocioException;
import com.example.MATRICULA.common.exception.RecursoNoEncontradoException;
import com.example.MATRICULA.common.service.BaseServiceImpl;
import com.example.MATRICULA.dto.anio.AnioAcademicoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnioAcademicoServiceImpl extends BaseServiceImpl implements AnioAcademicoService {

    private final AnioAcademicoRepository repo;

    @Override
    @Transactional(readOnly = true)
    public List<AnioAcademico> listarActivos() {
        return repo.findByEstadoTrueOrderByAnioDesc();
    }

    @Override
    @Transactional(readOnly = true)
    public AnioAcademico obtener(Integer idAnio) {
        return repo.findById(idAnio)
                .orElseThrow(() -> new RecursoNoEncontradoException("Año académico", idAnio));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AnioAcademico crear(AnioAcademicoRequest req) {
        if (repo.existsByAnio(req.getAnio()))
            throw new NegocioException("Ya existe el año académico " + req.getAnio());

        AnioAcademico nuevo = AnioAcademico.builder()
                .anio(req.getAnio())
                .descripcion(req.getDescripcion())
                .estado(Boolean.TRUE)
                .usuInsert(session.username())
                .build();
        AnioAcademico guardado = repo.save(nuevo);

        auditar(Constantes.MODULO_ANIOS, "anio_academico", Constantes.OP_INSERT,
                guardado.getIdAnio(), null, snapshot(guardado));
        return guardado;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void eliminar(Integer idAnio) {
        AnioAcademico existe = obtener(idAnio);
        Object antes = snapshot(existe);
        existe.setEstado(Boolean.FALSE);
        existe.setUsuUpdate(session.username());
        repo.save(existe);

        auditar(Constantes.MODULO_ANIOS, "anio_academico", Constantes.OP_DELETE,
                idAnio, antes, null);
    }

    private Map<String, Object> snapshot(AnioAcademico a) {
        return Map.of(
                "idAnio",      a.getIdAnio(),
                "anio",        a.getAnio(),
                "descripcion", a.getDescripcion() == null ? "" : a.getDescripcion(),
                "estado",      a.getEstado());
    }
}
