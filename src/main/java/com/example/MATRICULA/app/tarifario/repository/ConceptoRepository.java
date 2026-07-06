package com.example.MATRICULA.app.tarifario.repository;

import com.example.MATRICULA.app.tarifario.entity.Concepto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConceptoRepository extends JpaRepository<Concepto, Integer> {

    List<Concepto> findByAnioAcademicoIdAnioAndEstadoTrueOrderByOrden(Integer idAnio);

    boolean existsByAnioAcademicoIdAnioAndDescripcionIgnoreCaseAndEstadoTrue(Integer idAnio, String descripcion);

    long countByAnioAcademicoIdAnioAndEstadoTrue(Integer idAnio);

    /**
     * Devuelve el mayor "orden" registrado en un año, o 0 si no hay ninguno.
     * Se usa para auto-asignar el siguiente orden al crear un concepto nuevo.
     */
    @Query("SELECT COALESCE(MAX(c.orden), 0) FROM Concepto c " +
           "WHERE c.anioAcademico.idAnio = :idAnio AND c.estado = true")
    Integer maxOrdenByAnio(@Param("idAnio") Integer idAnio);

    /**
     * Conceptos activos de un tipo específico en un año.
     * Se usa antes de generar recurrentes: los previos se desactivan para
     * no acumular basura entre generaciones.
     */
    List<Concepto> findByAnioAcademicoIdAnioAndTipoConceptoIdTipoConcAndEstadoTrue(
            Integer idAnio, Integer idTipoConc);
}
