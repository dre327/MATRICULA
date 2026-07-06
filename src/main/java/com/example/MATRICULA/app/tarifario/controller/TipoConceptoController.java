package com.example.MATRICULA.app.tarifario.controller;

import com.example.MATRICULA.app.tarifario.facade.TipoConceptoFacade;
import com.example.MATRICULA.dto.tipoConcepto.TipoConceptoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Catálogo — accesible a cualquier usuario autenticado.
 * Se usa para poblar el dropdown en la pantalla de conceptos.
 */
@RestController
@RequestMapping("/api/tipos-concepto")
@RequiredArgsConstructor
public class TipoConceptoController {

    private final TipoConceptoFacade facade;

    @GetMapping
    public List<TipoConceptoResponse> listar() {
        return facade.listar();
    }
}
