package com.example.MATRICULA.app.reporte.facade;

import com.example.MATRICULA.dto.reporte.ReporteDeudaResponse;
import com.example.MATRICULA.dto.reporte.ReporteMatriculadoResponse;
import com.example.MATRICULA.dto.reporte.ReportePagoResponse;

import java.util.List;

public interface ReporteFacade {

    List<ReporteMatriculadoResponse> matriculadosPorAnio(Integer idAnio);

    List<ReportePagoResponse> pagosPorAnio(Integer idAnio);

    List<ReporteDeudaResponse> deudasPorAnio(Integer idAnio);

    List<ReporteDeudaResponse> deudasDeAlumnoPorAnio(Integer idAnio, Integer idAlumno);
}
