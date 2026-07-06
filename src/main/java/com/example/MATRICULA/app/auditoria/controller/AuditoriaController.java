package com.example.MATRICULA.app.auditoria.controller;

import com.example.MATRICULA.app.auditoria.facade.AuditoriaConsultaFacade;
import com.example.MATRICULA.dto.auditoria.AuditoriaFiltroRequest;
import com.example.MATRICULA.dto.auditoria.AuditoriaResponse;
import com.example.MATRICULA.dto.comun.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Consulta paginada de la bitácora de auditoría.
 *
 * NOTA: usa @PreAuthorize en vez de @RequierePermiso porque no existe una
 * funcionalidad "AUDITORIA" en la BD (no aparece en el menú del sistema).
 * Es admin-only por diseño — solo el Superusuario puede consultarla.
 */
@RestController
@RequestMapping("/api/auditoria")
@RequiredArgsConstructor
public class AuditoriaController {

    private final AuditoriaConsultaFacade facade;

    @GetMapping
    @PreAuthorize("hasRole('SUPERUSUARIO')")
    public PageResponse<AuditoriaResponse> consultar(@ModelAttribute AuditoriaFiltroRequest filtro) {
        return facade.consultar(filtro);
    }
}
