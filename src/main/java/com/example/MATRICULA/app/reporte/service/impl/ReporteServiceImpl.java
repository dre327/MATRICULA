package com.example.MATRICULA.app.reporte.service.impl;

import com.example.MATRICULA.app.reporte.entity.vista.ReporteDeudaVista;
import com.example.MATRICULA.app.reporte.entity.vista.ReporteMatriculadoVista;
import com.example.MATRICULA.app.reporte.entity.vista.ReportePagoVista;
import com.example.MATRICULA.app.reporte.repository.vista.ReporteDeudaVistaRepository;
import com.example.MATRICULA.app.reporte.repository.vista.ReporteMatriculadoVistaRepository;
import com.example.MATRICULA.app.reporte.repository.vista.ReportePagoVistaRepository;
import com.example.MATRICULA.app.reporte.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

    private final ReporteMatriculadoVistaRepository matriculadoRepo;
    private final ReportePagoVistaRepository pagoRepo;
    private final ReporteDeudaVistaRepository deudaRepo;

    @Override
    @Transactional(readOnly = true)
    public List<ReporteMatriculadoVista> matriculadosPorAnio(Integer idAnio) {
        return matriculadoRepo.findByIdAnioOrderByNivelAscGradoAscSeccionAscAlumnoAsc(idAnio);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReportePagoVista> pagosPorAnio(Integer idAnio) {
        return pagoRepo.findByIdAnioOrderByFechaDesc(idAnio);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReporteDeudaVista> deudasPorAnio(Integer idAnio) {
        return deudaRepo.findByIdAnioOrderByAlumnoAscOrdenAsc(idAnio);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReporteDeudaVista> deudasDeAlumnoPorAnio(Integer idAnio, Integer idAlumno) {
        return deudaRepo.findByIdAnioAndIdAlumnoOrderByOrden(idAnio, idAlumno);
    }
}
