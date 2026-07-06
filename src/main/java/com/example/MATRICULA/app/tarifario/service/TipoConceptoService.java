package com.example.MATRICULA.app.tarifario.service;

import com.example.MATRICULA.app.tarifario.entity.TipoConcepto;

import java.util.List;

public interface TipoConceptoService {

    List<TipoConcepto> listarActivos();

    TipoConcepto obtener(Integer idTipoConc);
}
