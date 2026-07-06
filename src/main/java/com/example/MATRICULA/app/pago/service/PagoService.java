package com.example.MATRICULA.app.pago.service;

import com.example.MATRICULA.app.matricula.entity.Cuota;
import com.example.MATRICULA.app.pago.entity.Recibo;
import com.example.MATRICULA.dto.pago.PagarRequest;

import java.util.List;

public interface PagoService {

    /**
     * Lista todas las cuotas de un alumno para un año, con flag "pagable"
     * (true solo para la primera cuota pendiente y las ya canceladas).
     */
    List<CuotaConEstado> listarDeudas(Integer idAlumno, Integer idAnio);

    /**
     * Ejecuta el pago de una cuota:
     *   - Valida orden (no cuota superior sin las anteriores).
     *   - Marca cuota como cancelada.
     *   - Toma correlativo con SELECT FOR UPDATE.
     *   - Genera Recibo con nroBoleta.
     *   - Audita.
     * Todo dentro de una transacción — rollback completo ante error.
     */
    Recibo pagar(PagarRequest req);

    /**
     * Registro auxiliar para el retorno de listarDeudas —
     * cuota + si es pagable ahora mismo.
     */
    record CuotaConEstado(Cuota cuota, boolean pagable) {}
}
