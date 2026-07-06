package com.example.MATRICULA.config.mapper;

import com.example.MATRICULA.app.tarifario.entity.Concepto;
import com.example.MATRICULA.app.tarifario.entity.TipoConcepto;
import com.example.MATRICULA.dto.concepto.ConceptoCreateRequest;
import com.example.MATRICULA.dto.concepto.ConceptoResponse;
import com.example.MATRICULA.dto.tipoConcepto.TipoConceptoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper del dominio Tarifario. Cubre: TipoConcepto y Concepto.
 */
@Mapper(componentModel = "spring")
public interface TarifarioMapper {

    // ── TipoConcepto ──────────────────────────────────────────────────

    TipoConceptoResponse toResponse(TipoConcepto entity);

    List<TipoConceptoResponse> toTipoConceptoResponseList(List<TipoConcepto> entities);

    // ── Concepto ──────────────────────────────────────────────────────

    @Mapping(target = "idAnio", source = "anioAcademico.idAnio")
    ConceptoResponse toResponse(Concepto entity);

    List<ConceptoResponse> toConceptoResponseList(List<Concepto> entities);

    /**
     * anioAcademico y tipoConcepto se resuelven en el service desde los ids
     * (necesita ir a BD). Aquí los ignoramos.
     */
    @Mapping(target = "idConcepto",     ignore = true)
    @Mapping(target = "anioAcademico",  ignore = true)
    @Mapping(target = "tipoConcepto",   ignore = true)
    @Mapping(target = "estado",         ignore = true)
    @Mapping(target = "version",        ignore = true)
    @Mapping(target = "usuInsert",      ignore = true)
    @Mapping(target = "fecInsert",      ignore = true)
    @Mapping(target = "usuUpdate",      ignore = true)
    @Mapping(target = "fecUpdate",      ignore = true)
    Concepto toEntity(ConceptoCreateRequest req);
}
