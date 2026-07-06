package com.example.MATRICULA.app.auditoria.repository.vista;

import com.example.MATRICULA.app.auditoria.entity.vista.AuditoriaVista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Vista de auditoría — permite filtros dinámicos vía Specifications
 * armadas en el service (ver AuditoriaVistaSpecifications).
 * El repositorio queda limpio: sin @Query, sin strings.
 */
public interface AuditoriaVistaRepository extends JpaRepository<AuditoriaVista, Long>, JpaSpecificationExecutor<AuditoriaVista> {
}
