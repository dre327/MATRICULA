package com.example.MATRICULA.app.reporte.repository.vista;

import com.example.MATRICULA.app.reporte.entity.vista.ReportePagoVista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ReportePagoVistaRepository extends JpaRepository<ReportePagoVista, Integer>, JpaSpecificationExecutor<ReportePagoVista> {

    List<ReportePagoVista> findByIdAnioOrderByFechaDesc(Integer idAnio);
}
