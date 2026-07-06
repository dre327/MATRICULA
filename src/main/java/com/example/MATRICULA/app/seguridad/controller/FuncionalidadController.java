package com.example.MATRICULA.app.seguridad.controller;

import com.example.MATRICULA.app.seguridad.facade.FuncionalidadFacade;
import com.example.MATRICULA.dto.funcionalidad.FuncionalidadResponse;
import com.example.MATRICULA.security.RequierePermiso;
import com.example.MATRICULA.security.TipoAccion;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/funcionalidades")
@RequiredArgsConstructor
public class FuncionalidadController {

    private final FuncionalidadFacade facade;

    /**
     * Se usa desde la pantalla de administración de permisos.
     * Devuelve todas las funcionalidades activas para renderizar el árbol de checkboxes.
     */
    @GetMapping
    @RequierePermiso(modulo = "PERMISOS", accion = TipoAccion.VER)
    public List<FuncionalidadResponse> listar() {
        return facade.listar();
    }
}
