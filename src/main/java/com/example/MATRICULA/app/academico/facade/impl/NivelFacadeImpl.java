package com.example.MATRICULA.app.academico.facade.impl;

import com.example.MATRICULA.app.academico.facade.NivelFacade;
import com.example.MATRICULA.app.academico.service.NivelService;
import com.example.MATRICULA.config.mapper.AcademicoMapper;
import com.example.MATRICULA.dto.nivel.NivelResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NivelFacadeImpl implements NivelFacade {

    private final NivelService service;
    private final AcademicoMapper mapper;

    @Override
    public List<NivelResponse> listar() {
        return mapper.toNivelResponseList(service.listarActivos());
    }
}
