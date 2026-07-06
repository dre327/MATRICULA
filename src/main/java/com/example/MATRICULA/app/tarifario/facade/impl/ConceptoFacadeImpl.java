package com.example.MATRICULA.app.tarifario.facade.impl;

import com.example.MATRICULA.app.tarifario.facade.ConceptoFacade;
import com.example.MATRICULA.app.tarifario.service.ConceptoService;
import com.example.MATRICULA.config.mapper.TarifarioMapper;
import com.example.MATRICULA.dto.concepto.ClonarConceptosRequest;
import com.example.MATRICULA.dto.concepto.ConceptoCreateRequest;
import com.example.MATRICULA.dto.concepto.ConceptoResponse;
import com.example.MATRICULA.dto.concepto.ConceptoUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConceptoFacadeImpl implements ConceptoFacade {

    private final ConceptoService service;
    private final TarifarioMapper mapper;

    @Override
    public List<ConceptoResponse> listarPorAnio(Integer idAnio) {
        return mapper.toConceptoResponseList(service.listarPorAnio(idAnio));
    }

    @Override
    public ConceptoResponse obtener(Integer idConcepto) {
        return mapper.toResponse(service.obtener(idConcepto));
    }

    @Override
    public List<ConceptoResponse> crear(ConceptoCreateRequest req) {
        return mapper.toConceptoResponseList(service.crear(req));
    }

    @Override
    public ConceptoResponse actualizar(Integer idConcepto, ConceptoUpdateRequest req) {
        return mapper.toResponse(service.actualizar(idConcepto, req));
    }

    @Override
    public void eliminar(Integer idConcepto) {
        service.eliminar(idConcepto);
    }

    @Override
    public List<ConceptoResponse> clonar(ClonarConceptosRequest req) {
        return mapper.toConceptoResponseList(
                service.clonar(req.getIdAnioOrigen(), req.getIdAnioDestino()));
    }
}
