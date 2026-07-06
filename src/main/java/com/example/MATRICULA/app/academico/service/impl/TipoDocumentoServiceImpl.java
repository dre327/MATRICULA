package com.example.MATRICULA.app.academico.service.impl;

import com.example.MATRICULA.app.academico.entity.TipoDocumento;
import com.example.MATRICULA.app.academico.repository.TipoDocumentoRepository;
import com.example.MATRICULA.app.academico.service.TipoDocumentoService;
import com.example.MATRICULA.common.exception.RecursoNoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoDocumentoServiceImpl implements TipoDocumentoService {

    private final TipoDocumentoRepository repo;

    @Override
    @Transactional(readOnly = true)
    public List<TipoDocumento> listarActivos() {
        return repo.findByEstadoTrueOrderByDescripcion();
    }

    @Override
    @Transactional(readOnly = true)
    public TipoDocumento obtener(Integer idTipoDoc) {
        return repo.findById(idTipoDoc)
                .orElseThrow(() -> new RecursoNoEncontradoException("Tipo de documento", idTipoDoc));
    }
}
