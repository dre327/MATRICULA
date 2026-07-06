package com.example.MATRICULA.app.academico.service.impl;

import com.example.MATRICULA.app.academico.entity.Nivel;
import com.example.MATRICULA.app.academico.repository.NivelRepository;
import com.example.MATRICULA.app.academico.service.NivelService;
import com.example.MATRICULA.common.exception.RecursoNoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NivelServiceImpl implements NivelService {

    private final NivelRepository repo;

    @Override
    @Transactional(readOnly = true)
    public List<Nivel> listarActivos() {
        return repo.findByEstadoTrueOrderByNombre();
    }

    @Override
    @Transactional(readOnly = true)
    public Nivel obtener(Integer idNivel) {
        return repo.findById(idNivel)
                .orElseThrow(() -> new RecursoNoEncontradoException("Nivel", idNivel));
    }
}
