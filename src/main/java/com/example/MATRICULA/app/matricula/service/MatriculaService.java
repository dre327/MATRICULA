package com.example.MATRICULA.app.matricula.service;

import com.example.MATRICULA.app.matricula.entity.Cuota;
import com.example.MATRICULA.app.matricula.entity.Matricula;
import com.example.MATRICULA.dto.matricula.MatricularRequest;

import java.util.List;

public interface MatriculaService {

    /**
     * Proceso principal: valida reglas + crea Matricula + genera Cuotas + audita.
     * Todo dentro de una transacción — si algo falla, rollback completo.
     */
    Matricula matricular(MatricularRequest req);

    Matricula obtenerPorAlumnoYAnio(Integer idAlumno, Integer idAnio);

    List<Cuota> listarCuotasDeMatricula(Integer idMatricula);
}
