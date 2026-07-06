package com.example.MATRICULA.app.academico.repository;

import com.example.MATRICULA.app.academico.entity.Aula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AulaRepository extends JpaRepository<Aula, Integer> {

    List<Aula> findByAnioAcademicoIdAnioAndEstadoTrueOrderByNivelIdNivelAscGradoAscSeccionAsc(Integer idAnio);

    boolean existsByAnioAcademicoIdAnioAndNivelIdNivelAndGradoAndSeccion(Integer idAnio, Integer idNivel, Integer grado, String seccion);
}
