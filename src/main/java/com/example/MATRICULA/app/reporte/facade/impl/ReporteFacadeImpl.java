package com.example.MATRICULA.app.reporte.facade.impl;

import com.example.MATRICULA.app.reporte.facade.ReporteFacade;
import com.example.MATRICULA.app.reporte.service.ReporteService;
import com.example.MATRICULA.config.mapper.ReporteMapper;
import com.example.MATRICULA.dto.reporte.ReporteDeudaResponse;
import com.example.MATRICULA.dto.reporte.ReporteMatriculadoResponse;
import com.example.MATRICULA.dto.reporte.ReportePagoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteFacadeImpl implements ReporteFacade {

    private final ReporteService service;
    private final ReporteMapper mapper;

    @Override
    public List<ReporteMatriculadoResponse> matriculadosPorAnio(Integer idAnio) {
        return mapper.toMatriculadoResponseList(service.matriculadosPorAnio(idAnio));
    }

    @Override
    public List<ReportePagoResponse> pagosPorAnio(Integer idAnio) {
        return mapper.toPagoResponseList(service.pagosPorAnio(idAnio));
    }

    @Override
    public List<ReporteDeudaResponse> deudasPorAnio(Integer idAnio) {
        return mapper.toDeudaResponseList(service.deudasPorAnio(idAnio));
    }

    @Override
    public List<ReporteDeudaResponse> deudasDeAlumnoPorAnio(Integer idAnio, Integer idAlumno) {
        return mapper.toDeudaResponseList(service.deudasDeAlumnoPorAnio(idAnio, idAlumno));
    }
}
