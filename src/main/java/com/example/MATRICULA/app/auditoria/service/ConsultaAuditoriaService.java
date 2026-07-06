package com.example.MATRICULA.app.auditoria.service;

import com.example.MATRICULA.app.auditoria.entity.vista.AuditoriaVista;
import com.example.MATRICULA.dto.auditoria.AuditoriaFiltroRequest;
import org.springframework.data.domain.Page;

public interface ConsultaAuditoriaService {

    Page<AuditoriaVista> consultar(AuditoriaFiltroRequest filtro);
}
