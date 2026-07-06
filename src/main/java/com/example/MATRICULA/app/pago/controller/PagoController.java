package com.example.MATRICULA.app.pago.controller;

import com.example.MATRICULA.app.pago.facade.PagoFacade;
import com.example.MATRICULA.dto.pago.DeudaResponse;
import com.example.MATRICULA.dto.pago.PagarRequest;
import com.example.MATRICULA.dto.pago.ReciboResponse;
import com.example.MATRICULA.security.RequierePermiso;
import com.example.MATRICULA.security.TipoAccion;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoFacade facade;

    /**
     * Lista todas las cuotas del alumno para un año, con flag `pagable`
     * (true solo para la primera pendiente — respeta el pago ordenado).
     */
    @GetMapping("/deudas")
    @RequierePermiso(modulo = "PAGOS", accion = TipoAccion.VER)
    public List<DeudaResponse> listarDeudas(
            @RequestParam Integer idAlumno,
            @RequestParam Integer idAnio) {
        return facade.listarDeudas(idAlumno, idAnio);
    }

    /**
     * Ejecuta el pago de una cuota — transacción completa:
     * valida orden + marca cuota cancelada + toma correlativo con FOR UPDATE
     * + inserta recibo + audita.
     */
    @PostMapping
    @RequierePermiso(modulo = "PAGOS", accion = TipoAccion.INSERTAR)
    public ReciboResponse pagar(@Valid @RequestBody PagarRequest req) {
        return facade.pagar(req);
    }
}
