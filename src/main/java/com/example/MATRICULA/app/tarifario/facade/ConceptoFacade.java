package com.example.MATRICULA.app.tarifario.facade;

import com.example.MATRICULA.dto.concepto.ClonarConceptosRequest;
import com.example.MATRICULA.dto.concepto.ConceptoCreateRequest;
import com.example.MATRICULA.dto.concepto.ConceptoResponse;
import com.example.MATRICULA.dto.concepto.ConceptoUpdateRequest;

import java.util.List;

public interface ConceptoFacade {

    List<ConceptoResponse> listarPorAnio(Integer idAnio);

    ConceptoResponse obtener(Integer idConcepto);

    /** Lista porque para tipos recurrentes (MENSUALIDAD) se crean N conceptos. */
    List<ConceptoResponse> crear(ConceptoCreateRequest req);

    ConceptoResponse actualizar(Integer idConcepto, ConceptoUpdateRequest req);

    void eliminar(Integer idConcepto);

    List<ConceptoResponse> clonar(ClonarConceptosRequest req);
}
