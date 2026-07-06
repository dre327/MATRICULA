package com.example.MATRICULA.app.matricula.repository;

import com.example.MATRICULA.app.matricula.entity.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface MatriculaRepository extends JpaRepository<Matricula, Integer>, JpaSpecificationExecutor<Matricula> {

    boolean existsByAlumnoIdAlumnoAndAnioAcademicoIdAnioAndEstadoTrue(Integer idAlumno, Integer idAnio);

    Optional<Matricula> findByAlumnoIdAlumnoAndAnioAcademicoIdAnioAndEstadoTrue(Integer idAlumno, Integer idAnio);

    List<Matricula> findByAulaIdAulaAndEstadoTrue(Integer idAula);

    long countByAulaIdAulaAndEstadoTrue(Integer idAula);
}
