package com.example.MATRICULA.app.reporte.repository.vista;

import com.example.MATRICULA.app.reporte.entity.vista.ReporteDeudaVista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ReporteDeudaVistaRepository extends JpaRepository<ReporteDeudaVista, Integer>, JpaSpecificationExecutor<ReporteDeudaVista> {

    List<ReporteDeudaVista> findByIdAnioOrderByAlumnoAscOrdenAsc(Integer idAnio);

    List<ReporteDeudaVista> findByIdAnioAndIdAlumnoOrderByOrden(Integer idAnio, Integer idAlumno);
}
