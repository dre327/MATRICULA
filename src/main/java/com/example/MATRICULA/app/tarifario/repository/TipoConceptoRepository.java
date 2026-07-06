package com.example.MATRICULA.app.tarifario.repository;

import com.example.MATRICULA.app.tarifario.entity.TipoConcepto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipoConceptoRepository extends JpaRepository<TipoConcepto, Integer> {

    List<TipoConcepto> findByEstadoTrueOrderByDescripcion();
}
