package com.example.MATRICULA.app.auditoria.facade.impl;

import com.example.MATRICULA.app.auditoria.entity.vista.AuditoriaVista;
import com.example.MATRICULA.app.auditoria.facade.AuditoriaConsultaFacade;
import com.example.MATRICULA.app.auditoria.service.ConsultaAuditoriaService;
import com.example.MATRICULA.config.mapper.AuditoriaMapper;
import com.example.MATRICULA.dto.auditoria.AuditoriaFiltroRequest;
import com.example.MATRICULA.dto.auditoria.AuditoriaResponse;
import com.example.MATRICULA.dto.comun.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditoriaConsultaFacadeImpl implements AuditoriaConsultaFacade {

    private final ConsultaAuditoriaService service;
    private final AuditoriaMapper mapper;

    @Override
    public PageResponse<AuditoriaResponse> consultar(AuditoriaFiltroRequest filtro) {
        Page<AuditoriaVista> page = service.consultar(filtro);
        return PageResponse.of(page.map(mapper::toResponse));
    }
}
