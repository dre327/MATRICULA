package com.example.MATRICULA.app.tarifario.facade.impl;

import com.example.MATRICULA.app.tarifario.facade.TipoConceptoFacade;
import com.example.MATRICULA.app.tarifario.service.TipoConceptoService;
import com.example.MATRICULA.config.mapper.TarifarioMapper;
import com.example.MATRICULA.dto.tipoConcepto.TipoConceptoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoConceptoFacadeImpl implements TipoConceptoFacade {

    private final TipoConceptoService service;
    private final TarifarioMapper mapper;

    @Override
    public List<TipoConceptoResponse> listar() {
        return mapper.toTipoConceptoResponseList(service.listarActivos());
    }
}
