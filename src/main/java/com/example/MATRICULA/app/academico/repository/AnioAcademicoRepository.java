package com.example.MATRICULA.app.academico.repository;

import com.example.MATRICULA.app.academico.entity.AnioAcademico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnioAcademicoRepository extends JpaRepository<AnioAcademico, Integer> {

    List<AnioAcademico> findByEstadoTrueOrderByAnioDesc();

    Optional<AnioAcademico> findByAnio(Integer anio);

    boolean existsByAnio(Integer anio);
}
