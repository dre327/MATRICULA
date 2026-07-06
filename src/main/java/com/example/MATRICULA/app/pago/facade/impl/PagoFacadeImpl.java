package com.example.MATRICULA.app.pago.facade.impl;

import com.example.MATRICULA.app.pago.facade.PagoFacade;
import com.example.MATRICULA.app.pago.service.PagoService;
import com.example.MATRICULA.config.mapper.PagoMapper;
import com.example.MATRICULA.dto.pago.DeudaResponse;
import com.example.MATRICULA.dto.pago.PagarRequest;
import com.example.MATRICULA.dto.pago.ReciboResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PagoFacadeImpl implements PagoFacade {

    private final PagoService service;
    private final PagoMapper mapper;

    /**
     * Convierte la lista de CuotaConEstado (del service) en DeudaResponse
     * (con estadoPago como String y el flag pagable calculado).
     */
    @Override
    public List<DeudaResponse> listarDeudas(Integer idAlumno, Integer idAnio) {
        return service.listarDeudas(idAlumno, idAnio).stream()
                .map(x -> DeudaResponse.builder()
                        .idCuota(x.cuota().getIdCuota())
                        .orden(x.cuota().getConcepto().getOrden())
                        .concepto(x.cuota().getConcepto().getDescripcion())
                        .monto(x.cuota().getMonto())
                        .estadoPago(String.valueOf(x.cuota().getEstadoPago()))
                        .pagable(x.pagable())
                        .build())
                .toList();
    }

    @Override
    public ReciboResponse pagar(PagarRequest req) {
        return mapper.toResponse(service.pagar(req));
    }
}
