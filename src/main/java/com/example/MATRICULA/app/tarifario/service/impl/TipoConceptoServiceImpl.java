package com.example.MATRICULA.app.tarifario.service.impl;

import com.example.MATRICULA.app.tarifario.entity.TipoConcepto;
import com.example.MATRICULA.app.tarifario.repository.TipoConceptoRepository;
import com.example.MATRICULA.app.tarifario.service.TipoConceptoService;
import com.example.MATRICULA.common.exception.RecursoNoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoConceptoServiceImpl implements TipoConceptoService {

    private final TipoConceptoRepository repo;

    @Override
    @Transactional(readOnly = true)
    public List<TipoConcepto> listarActivos() {
        return repo.findByEstadoTrueOrderByDescripcion();
    }

    @Override
    @Transactional(readOnly = true)
    public TipoConcepto obtener(Integer idTipoConc) {
        return repo.findById(idTipoConc)
                .orElseThrow(() -> new RecursoNoEncontradoException("Tipo de concepto", idTipoConc));
    }
}
