package com.example.MATRICULA.app.academico.facade.impl;

import com.example.MATRICULA.app.academico.facade.AnioAcademicoFacade;
import com.example.MATRICULA.app.academico.service.AnioAcademicoService;
import com.example.MATRICULA.config.mapper.AcademicoMapper;
import com.example.MATRICULA.dto.anio.AnioAcademicoRequest;
import com.example.MATRICULA.dto.anio.AnioAcademicoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnioAcademicoFacadeImpl implements AnioAcademicoFacade {

    private final AnioAcademicoService service;
    private final AcademicoMapper mapper;

    @Override
    public List<AnioAcademicoResponse> listar() {
        return mapper.toAnioResponseList(service.listarActivos());
    }

    @Override
    public AnioAcademicoResponse obtener(Integer idAnio) {
        return mapper.toResponse(service.obtener(idAnio));
    }

    @Override
    public AnioAcademicoResponse crear(AnioAcademicoRequest req) {
        return mapper.toResponse(service.crear(req));
    }

    @Override
    public void eliminar(Integer idAnio) {
        service.eliminar(idAnio);
    }
}
