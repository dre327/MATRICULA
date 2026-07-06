package com.example.MATRICULA.app.academico.controller;

import com.example.MATRICULA.app.academico.facade.NivelFacade;
import com.example.MATRICULA.dto.nivel.NivelResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Catálogo — accesible a cualquier usuario autenticado.
 * Se usa para poblar dropdowns en pantallas de aula y matrícula.
 */
@RestController
@RequestMapping("/api/niveles")
@RequiredArgsConstructor
public class NivelController {

    private final NivelFacade facade;

    @GetMapping
    public List<NivelResponse> listar() {
        return facade.listar();
    }
}
