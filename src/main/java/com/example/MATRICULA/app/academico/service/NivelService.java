package com.example.MATRICULA.app.academico.service;

import com.example.MATRICULA.app.academico.entity.Nivel;

import java.util.List;

public interface NivelService {

    List<Nivel> listarActivos();

    Nivel obtener(Integer idNivel);
}
