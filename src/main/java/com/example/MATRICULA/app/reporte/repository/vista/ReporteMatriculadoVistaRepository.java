package com.example.MATRICULA.app.reporte.repository.vista;

import com.example.MATRICULA.app.reporte.entity.vista.ReporteMatriculadoVista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ReporteMatriculadoVistaRepository extends JpaRepository<ReporteMatriculadoVista, Integer>, JpaSpecificationExecutor<ReporteMatriculadoVista> {

    List<ReporteMatriculadoVista> findByIdAnioOrderByNivelAscGradoAscSeccionAscAlumnoAsc(Integer idAnio);
}
