package com.example.MATRICULA.app.seguridad.facade.impl;

import com.example.MATRICULA.app.seguridad.facade.FuncionalidadFacade;
import com.example.MATRICULA.app.seguridad.service.FuncionalidadService;
import com.example.MATRICULA.config.mapper.SeguridadMapper;
import com.example.MATRICULA.dto.funcionalidad.FuncionalidadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FuncionalidadFacadeImpl implements FuncionalidadFacade {

    private final FuncionalidadService service;
    private final SeguridadMapper mapper;

    @Override
    public List<FuncionalidadResponse> listar() {
        return mapper.toFuncionalidadResponseList(service.listarActivos());
    }
}
