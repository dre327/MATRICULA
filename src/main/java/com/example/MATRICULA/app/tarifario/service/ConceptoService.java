package com.example.MATRICULA.app.tarifario.service;

import com.example.MATRICULA.app.tarifario.entity.Concepto;
import com.example.MATRICULA.dto.concepto.ConceptoCreateRequest;
import com.example.MATRICULA.dto.concepto.ConceptoUpdateRequest;

import java.util.List;

public interface ConceptoService {

    List<Concepto> listarPorAnio(Integer idAnio);

    Concepto obtener(Integer idConcepto);

    /**
     * Crea uno o más conceptos:
     *   - Si el tipo NO es recurrente: crea un solo concepto.
     *   - Si el tipo es recurrente (MENSUALIDAD): auto-genera N conceptos
     *     con sufijos de mes (MARZO, ABRIL, ...) y órdenes secuenciales.
     * Devuelve la lista de conceptos creados.
     */
    List<Concepto> crear(ConceptoCreateRequest req);

    Concepto actualizar(Integer idConcepto, ConceptoUpdateRequest req);

    void eliminar(Integer idConcepto);

    /**
     * Copia todos los conceptos activos de idAnioOrigen hacia idAnioDestino.
     * Requerido por el documento como "herramienta para clonar conceptos".
     */
    List<Concepto> clonar(Integer idAnioOrigen, Integer idAnioDestino);
}
