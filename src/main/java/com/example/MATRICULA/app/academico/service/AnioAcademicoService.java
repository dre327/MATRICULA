package com.example.MATRICULA.app.academico.service;

import com.example.MATRICULA.app.academico.entity.AnioAcademico;
import com.example.MATRICULA.dto.anio.AnioAcademicoRequest;

import java.util.List;

public interface AnioAcademicoService {

    List<AnioAcademico> listarActivos();

    AnioAcademico obtener(Integer idAnio);

    AnioAcademico crear(AnioAcademicoRequest req);

    void eliminar(Integer idAnio);
}
