package com.example.MATRICULA.app.academico.repository;

import com.example.MATRICULA.app.academico.entity.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Integer> {

    List<TipoDocumento> findByEstadoTrueOrderByDescripcion();
}
