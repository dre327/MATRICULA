package com.example.MATRICULA.app.matricula.controller;

import com.example.MATRICULA.app.matricula.facade.MatriculaFacade;
import com.example.MATRICULA.dto.matricula.MatriculaResponse;
import com.example.MATRICULA.dto.matricula.MatricularRequest;
import com.example.MATRICULA.security.RequierePermiso;
import com.example.MATRICULA.security.TipoAccion;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/matriculas")
@RequiredArgsConstructor
public class MatriculaController {

    private final MatriculaFacade facade;

    /**
     * Proceso principal — dispara la transacción completa:
     * validaciones + insert matrícula + insert N cuotas + auditoría.
     */
    @PostMapping
    @RequierePermiso(modulo = "MATRICULA", accion = TipoAccion.INSERTAR)
    public MatriculaResponse matricular(@Valid @RequestBody MatricularRequest req) {
        return facade.matricular(req);
    }
}
