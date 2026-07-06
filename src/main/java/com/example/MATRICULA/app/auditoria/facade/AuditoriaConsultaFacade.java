package com.example.MATRICULA.app.auditoria.facade;

import com.example.MATRICULA.dto.auditoria.AuditoriaFiltroRequest;
import com.example.MATRICULA.dto.auditoria.AuditoriaResponse;
import com.example.MATRICULA.dto.comun.PageResponse;

public interface AuditoriaConsultaFacade {

    PageResponse<AuditoriaResponse> consultar(AuditoriaFiltroRequest filtro);
}
