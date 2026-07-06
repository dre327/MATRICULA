package com.example.MATRICULA.config.mapper;

import com.example.MATRICULA.app.reporte.entity.vista.ReporteDeudaVista;
import com.example.MATRICULA.app.reporte.entity.vista.ReporteMatriculadoVista;
import com.example.MATRICULA.app.reporte.entity.vista.ReportePagoVista;
import com.example.MATRICULA.dto.reporte.ReporteDeudaResponse;
import com.example.MATRICULA.dto.reporte.ReporteMatriculadoResponse;
import com.example.MATRICULA.dto.reporte.ReportePagoResponse;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper de reportes. Convierte las vistas planas de reporte a sus respectivos DTOs.
 * Las vistas ya vienen aplanadas desde BD — el mapper es casi 1-a-1.
 */
@Mapper(componentModel = "spring")
public interface ReporteMapper {

    ReporteMatriculadoResponse toResponse(ReporteMatriculadoVista vista);
    List<ReporteMatriculadoResponse> toMatriculadoResponseList(List<ReporteMatriculadoVista> vistas);

    ReportePagoResponse toResponse(ReportePagoVista vista);
    List<ReportePagoResponse> toPagoResponseList(List<ReportePagoVista> vistas);

    ReporteDeudaResponse toResponse(ReporteDeudaVista vista);
    List<ReporteDeudaResponse> toDeudaResponseList(List<ReporteDeudaVista> vistas);
}
