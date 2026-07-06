package com.example.MATRICULA.app.academico.facade.impl;

import com.example.MATRICULA.app.academico.facade.AulaFacade;
import com.example.MATRICULA.app.academico.service.AulaService;
import com.example.MATRICULA.config.mapper.AcademicoMapper;
import com.example.MATRICULA.dto.aula.AulaCreateRequest;
import com.example.MATRICULA.dto.aula.AulaCupoResponse;
import com.example.MATRICULA.dto.aula.AulaResponse;
import com.example.MATRICULA.dto.aula.AulaUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AulaFacadeImpl implements AulaFacade {

    private final AulaService service;
    private final AcademicoMapper mapper;

    @Override
    public List<AulaResponse> listarPorAnio(Integer idAnio) {
        return mapper.toAulaResponseList(service.listarPorAnio(idAnio));
    }

    @Override
    public List<AulaCupoResponse> listarCuposPorAnio(Integer idAnio) {
        return mapper.toAulaCupoResponseList(service.listarCuposPorAnio(idAnio));
    }

    @Override
    public AulaResponse obtener(Integer idAula) {
        return mapper.toResponse(service.obtener(idAula));
    }

    @Override
    public AulaResponse crear(AulaCreateRequest req) {
        return mapper.toResponse(service.crear(req));
    }

    @Override
    public AulaResponse actualizar(Integer idAula, AulaUpdateRequest req) {
        return mapper.toResponse(service.actualizar(idAula, req));
    }

    @Override
    public void eliminar(Integer idAula) {
        service.eliminar(idAula);
    }
}
