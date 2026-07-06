package com.example.MATRICULA.app.academico.repository.vista;

import com.example.MATRICULA.app.academico.entity.vista.AulaCupoVista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AulaCupoVistaRepository extends JpaRepository<AulaCupoVista, Integer> {

    List<AulaCupoVista> findByIdAnioOrderByIdNivelAscGradoAscSeccionAsc(Integer idAnio);
}
