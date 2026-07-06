package com.example.MATRICULA.config.mapper;

import com.example.MATRICULA.app.auditoria.entity.vista.AuditoriaVista;
import com.example.MATRICULA.dto.auditoria.AuditoriaResponse;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper de la bitácora de auditoría. Solo convierte de vw_auditoria_detalle a DTO.
 * La inserción en auditoria se hace directamente en AuditoriaService (no requiere mapper).
 */
@Mapper(componentModel = "spring")
public interface AuditoriaMapper {

    AuditoriaResponse toResponse(AuditoriaVista vista);

    List<AuditoriaResponse> toAuditoriaResponseList(List<AuditoriaVista> vistas);
}
