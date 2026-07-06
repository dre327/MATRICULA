package com.example.MATRICULA.config.mapper;

import com.example.MATRICULA.app.academico.entity.*;
import com.example.MATRICULA.app.academico.entity.vista.AulaCupoVista;
import com.example.MATRICULA.dto.alumno.AlumnoCreateRequest;
import com.example.MATRICULA.dto.alumno.AlumnoResponse;
import com.example.MATRICULA.dto.anio.AnioAcademicoRequest;
import com.example.MATRICULA.dto.anio.AnioAcademicoResponse;
import com.example.MATRICULA.dto.aula.AulaCreateRequest;
import com.example.MATRICULA.dto.aula.AulaCupoResponse;
import com.example.MATRICULA.dto.aula.AulaResponse;
import com.example.MATRICULA.dto.nivel.NivelRequest;
import com.example.MATRICULA.dto.nivel.NivelResponse;
import com.example.MATRICULA.dto.tipoDocumento.TipoDocumentoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper del dominio Académico. Cubre: AnioAcademico, Nivel, TipoDocumento,
 * Alumno, Aula y la vista AulaCupoVista.
 */
@Mapper(componentModel = "spring")
public interface AcademicoMapper {

    // ── AnioAcademico ─────────────────────────────────────────────────

    AnioAcademicoResponse toResponse(AnioAcademico entity);

    List<AnioAcademicoResponse> toAnioResponseList(List<AnioAcademico> entities);

    @Mapping(target = "idAnio",    ignore = true)
    @Mapping(target = "estado",    ignore = true)
    @Mapping(target = "usuInsert", ignore = true)
    @Mapping(target = "fecInsert", ignore = true)
    @Mapping(target = "usuUpdate", ignore = true)
    @Mapping(target = "fecUpdate", ignore = true)
    AnioAcademico toEntity(AnioAcademicoRequest req);

    // ── Nivel ─────────────────────────────────────────────────────────

    NivelResponse toResponse(Nivel entity);

    List<NivelResponse> toNivelResponseList(List<Nivel> entities);

    @Mapping(target = "idNivel",   ignore = true)
    @Mapping(target = "estado",    ignore = true)
    @Mapping(target = "usuInsert", ignore = true)
    @Mapping(target = "fecInsert", ignore = true)
    @Mapping(target = "usuUpdate", ignore = true)
    @Mapping(target = "fecUpdate", ignore = true)
    Nivel toEntity(NivelRequest req);

    // ── TipoDocumento ─────────────────────────────────────────────────

    TipoDocumentoResponse toResponse(TipoDocumento entity);

    List<TipoDocumentoResponse> toTipoDocumentoResponseList(List<TipoDocumento> entities);

    // ── Alumno ────────────────────────────────────────────────────────

    /**
     * Al mapear el response se aplana el nombre completo con concat.
     * MapStruct puede llamar expresiones Java para campos derivados.
     */
    @Mapping(target = "nombreCompleto",
             expression = "java(entity.getApPaterno() + \" \" + entity.getApMaterno() + \", \" + entity.getNombre())")
    AlumnoResponse toResponse(Alumno entity);

    List<AlumnoResponse> toAlumnoResponseList(List<Alumno> entities);

    /**
     * El tipoDocumento se resuelve en el service desde idTipoDocumento
     * (necesita ir a BD). Aquí lo ignoramos.
     */
    @Mapping(target = "idAlumno",       ignore = true)
    @Mapping(target = "tipoDocumento",  ignore = true)
    @Mapping(target = "estado",         ignore = true)
    @Mapping(target = "usuInsert",      ignore = true)
    @Mapping(target = "fecInsert",      ignore = true)
    @Mapping(target = "usuUpdate",      ignore = true)
    @Mapping(target = "fecUpdate",      ignore = true)
    @Mapping(target = "sexo",           source = "sexo", qualifiedByName = "stringToChar")
    Alumno toEntity(AlumnoCreateRequest req);

    @org.mapstruct.Named("stringToChar")
    default Character stringToChar(String s) {
        return (s == null || s.isBlank()) ? null : s.charAt(0);
    }

    @org.mapstruct.Named("charToString")
    default String charToString(Character c) {
        return c == null ? null : c.toString();
    }

    // ── Aula ──────────────────────────────────────────────────────────

    AulaResponse toResponse(Aula entity);

    List<AulaResponse> toAulaResponseList(List<Aula> entities);

    /**
     * anioAcademico y nivel se resuelven en el service desde los ids
     * (necesita ir a BD). Aquí los ignoramos.
     */
    @Mapping(target = "idAula",         ignore = true)
    @Mapping(target = "anioAcademico",  ignore = true)
    @Mapping(target = "nivel",          ignore = true)
    @Mapping(target = "estado",         ignore = true)
    @Mapping(target = "version",        ignore = true)
    @Mapping(target = "usuInsert",      ignore = true)
    @Mapping(target = "fecInsert",      ignore = true)
    @Mapping(target = "usuUpdate",      ignore = true)
    @Mapping(target = "fecUpdate",      ignore = true)
    Aula toEntity(AulaCreateRequest req);

    // ── Vista AulaCupo ────────────────────────────────────────────────

    /**
     * Cupos disponibles se calcula como capacidad - ocupados.
     */
    @Mapping(target = "disponibles",
             expression = "java(vista.getCapacidad() - vista.getOcupados())")
    AulaCupoResponse toResponse(AulaCupoVista vista);

    List<AulaCupoResponse> toAulaCupoResponseList(List<AulaCupoVista> vistas);
}
