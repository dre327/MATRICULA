package com.example.MATRICULA.app.academico.service;

import com.example.MATRICULA.app.academico.entity.TipoDocumento;

import java.util.List;

public interface TipoDocumentoService {

    List<TipoDocumento> listarActivos();

    TipoDocumento obtener(Integer idTipoDoc);
}
