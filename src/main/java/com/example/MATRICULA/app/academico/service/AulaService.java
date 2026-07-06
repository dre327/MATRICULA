package com.example.MATRICULA.app.academico.service;

import com.example.MATRICULA.app.academico.entity.Aula;
import com.example.MATRICULA.app.academico.entity.vista.AulaCupoVista;
import com.example.MATRICULA.dto.aula.AulaCreateRequest;
import com.example.MATRICULA.dto.aula.AulaUpdateRequest;

import java.util.List;

public interface AulaService {

    List<Aula> listarPorAnio(Integer idAnio);

    List<AulaCupoVista> listarCuposPorAnio(Integer idAnio);

    Aula obtener(Integer idAula);

    Aula crear(AulaCreateRequest req);

    Aula actualizar(Integer idAula, AulaUpdateRequest req);

    void eliminar(Integer idAula);
}
