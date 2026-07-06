package com.example.MATRICULA.app.academico.facade;

import com.example.MATRICULA.dto.tipoDocumento.TipoDocumentoResponse;

import java.util.List;

public interface TipoDocumentoFacade {

    List<TipoDocumentoResponse> listar();
}
