package com.example.MATRICULA.app.reporte.service;

import com.example.MATRICULA.app.reporte.entity.vista.ReporteDeudaVista;
import com.example.MATRICULA.app.reporte.entity.vista.ReporteMatriculadoVista;
import com.example.MATRICULA.app.reporte.entity.vista.ReportePagoVista;

import java.util.List;

public interface ReporteService {

    List<ReporteMatriculadoVista> matriculadosPorAnio(Integer idAnio);

    List<ReportePagoVista> pagosPorAnio(Integer idAnio);

    List<ReporteDeudaVista> deudasPorAnio(Integer idAnio);

    List<ReporteDeudaVista> deudasDeAlumnoPorAnio(Integer idAnio, Integer idAlumno);
}
