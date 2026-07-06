package com.example.MATRICULA.app.academico.facade;

import com.example.MATRICULA.dto.aula.AulaCreateRequest;
import com.example.MATRICULA.dto.aula.AulaCupoResponse;
import com.example.MATRICULA.dto.aula.AulaResponse;
import com.example.MATRICULA.dto.aula.AulaUpdateRequest;

import java.util.List;

public interface AulaFacade {

    List<AulaResponse> listarPorAnio(Integer idAnio);

    List<AulaCupoResponse> listarCuposPorAnio(Integer idAnio);

    AulaResponse obtener(Integer idAula);

    AulaResponse crear(AulaCreateRequest req);

    AulaResponse actualizar(Integer idAula, AulaUpdateRequest req);

    void eliminar(Integer idAula);
}
