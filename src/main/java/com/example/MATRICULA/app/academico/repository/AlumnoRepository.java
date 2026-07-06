package com.example.MATRICULA.app.academico.repository;

import com.example.MATRICULA.app.academico.entity.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AlumnoRepository extends JpaRepository<Alumno, Integer>, JpaSpecificationExecutor<Alumno> {

    Optional<Alumno> findByTipoDocumentoIdTipoDocAndNroDocumento(Integer idTipoDoc, String nroDocumento);

    boolean existsByTipoDocumentoIdTipoDocAndNroDocumento(Integer idTipoDoc, String nroDocumento);
}
