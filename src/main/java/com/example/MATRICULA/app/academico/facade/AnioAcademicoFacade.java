package com.example.MATRICULA.app.academico.facade;

import com.example.MATRICULA.dto.anio.AnioAcademicoRequest;
import com.example.MATRICULA.dto.anio.AnioAcademicoResponse;

import java.util.List;

public interface AnioAcademicoFacade {

    List<AnioAcademicoResponse> listar();

    AnioAcademicoResponse obtener(Integer idAnio);

    AnioAcademicoResponse crear(AnioAcademicoRequest req);

    void eliminar(Integer idAnio);
}
