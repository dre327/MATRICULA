package com.example.MATRICULA.app.academico.repository;

import com.example.MATRICULA.app.academico.entity.Nivel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NivelRepository extends JpaRepository<Nivel, Integer> {

    List<Nivel> findByEstadoTrueOrderByNombre();

    boolean existsByNombreIgnoreCase(String nombre);
}
