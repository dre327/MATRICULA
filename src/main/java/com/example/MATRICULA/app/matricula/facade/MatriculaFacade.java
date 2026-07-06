package com.example.MATRICULA.app.matricula.facade;

import com.example.MATRICULA.dto.matricula.MatriculaResponse;
import com.example.MATRICULA.dto.matricula.MatricularRequest;

public interface MatriculaFacade {

    /** Ejecuta la matrícula y devuelve el response incluyendo las cuotas generadas. */
    MatriculaResponse matricular(MatricularRequest req);
}
