package com.example.MATRICULA.app.academico.facade.impl;

import com.example.MATRICULA.app.academico.facade.TipoDocumentoFacade;
import com.example.MATRICULA.app.academico.service.TipoDocumentoService;
import com.example.MATRICULA.config.mapper.AcademicoMapper;
import com.example.MATRICULA.dto.tipoDocumento.TipoDocumentoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoDocumentoFacadeImpl implements TipoDocumentoFacade {

    private final TipoDocumentoService service;
    private final AcademicoMapper mapper;

    @Override
    public List<TipoDocumentoResponse> listar() {
        return mapper.toTipoDocumentoResponseList(service.listarActivos());
    }
}
