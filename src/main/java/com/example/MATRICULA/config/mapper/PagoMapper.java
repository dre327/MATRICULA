package com.example.MATRICULA.config.mapper;

import com.example.MATRICULA.app.pago.entity.Recibo;
import com.example.MATRICULA.dto.pago.ReciboResponse;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper del dominio Pago. Convierte Recibo a DTO.
 * DeudaResponse no se genera aquí — se construye directamente en el service
 * porque necesita el flag "pagable" que depende de reglas de negocio (orden).
 */
@Mapper(componentModel = "spring", uses = { MatriculaMapper.class })
public interface PagoMapper {

    ReciboResponse toResponse(Recibo entity);

    List<ReciboResponse> toReciboResponseList(List<Recibo> entities);
}
