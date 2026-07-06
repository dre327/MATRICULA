package com.example.MATRICULA.config.mapper;

import com.example.MATRICULA.app.matricula.entity.Cuota;
import com.example.MATRICULA.app.matricula.entity.Matricula;
import com.example.MATRICULA.dto.cuota.CuotaResponse;
import com.example.MATRICULA.dto.matricula.MatriculaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * Mapper del dominio Matrícula. Convierte entidades Matricula y Cuota a DTOs.
 * Depende de AcademicoMapper (para anio/aula/alumno) y TarifarioMapper (para concepto).
 */
@Mapper(componentModel = "spring",
        uses = { AcademicoMapper.class, TarifarioMapper.class })
public interface MatriculaMapper {

    // ── Matricula ─────────────────────────────────────────────────────

    /**
     * Al mapear cuotas: NO se pasan aquí (evita cargas lazy inesperadas).
     * El service las carga explícitamente y las setea después del mapeo si aplica.
     */
    @Mapping(target = "cuotas", ignore = true)
    MatriculaResponse toResponse(Matricula entity);

    List<MatriculaResponse> toMatriculaResponseList(List<Matricula> entities);

    // ── Cuota ─────────────────────────────────────────────────────────

    @Mapping(target = "idMatricula", source = "matricula.idMatricula")
    @Mapping(target = "estadoPago",  source = "estadoPago", qualifiedByName = "charToStringPago")
    CuotaResponse toResponse(Cuota entity);

    List<CuotaResponse> toCuotaResponseList(List<Cuota> entities);

    @Named("charToStringPago")
    default String charToStringPago(Character c) {
        return c == null ? null : c.toString();
    }
}
