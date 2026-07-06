package com.example.MATRICULA.app.seguridad.controller;

import com.example.MATRICULA.app.seguridad.facade.PermisoFacade;
import com.example.MATRICULA.dto.permiso.AplicarPermisosRequest;
import com.example.MATRICULA.dto.permiso.PermisoResponse;
import com.example.MATRICULA.security.RequierePermiso;
import com.example.MATRICULA.security.TipoAccion;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permisos")
@RequiredArgsConstructor
public class PermisoController {

    private final PermisoFacade facade;

    /**
     * Devuelve los permisos configurados para un rol.
     * El frontend arma la grilla de checkboxes cargándolos como estado inicial.
     */
    @GetMapping("/rol/{idRol}")
    @RequierePermiso(modulo = "PERMISOS", accion = TipoAccion.VER)
    public List<PermisoResponse> listarPorRol(@PathVariable Integer idRol) {
        return facade.listarPorRol(idRol);
    }

    /**
     * Aplica en batch todos los permisos del rol (borra los existentes + inserta los nuevos).
     * Botón "Aplicar" de la pantalla de permisos según el documento.
     */
    @PostMapping("/aplicar")
    @RequierePermiso(modulo = "PERMISOS", accion = TipoAccion.EDITAR)
    public ResponseEntity<Void> aplicar(@Valid @RequestBody AplicarPermisosRequest req) {
        facade.aplicar(req);
        return ResponseEntity.noContent().build();
    }
}
