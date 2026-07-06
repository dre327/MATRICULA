package com.example.MATRICULA.app.pago.facade;

import com.example.MATRICULA.dto.pago.DeudaResponse;
import com.example.MATRICULA.dto.pago.PagarRequest;
import com.example.MATRICULA.dto.pago.ReciboResponse;

import java.util.List;

public interface PagoFacade {

    List<DeudaResponse> listarDeudas(Integer idAlumno, Integer idAnio);

    ReciboResponse pagar(PagarRequest req);
}
