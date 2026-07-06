package com.example.MATRICULA.app.academico.controller;

import com.example.MATRICULA.app.academico.facade.TipoDocumentoFacade;
import com.example.MATRICULA.dto.tipoDocumento.TipoDocumentoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Catálogo — accesible a cualquier usuario autenticado.
 * Se usa para poblar el dropdown en la pantalla de alumno.
 */
@RestController
@RequestMapping("/api/tipos-documento")
@RequiredArgsConstructor
public class TipoDocumentoController {

    private final TipoDocumentoFacade facade;

    @GetMapping
    public List<TipoDocumentoResponse> listar() {
        return facade.listar();
    }
}
