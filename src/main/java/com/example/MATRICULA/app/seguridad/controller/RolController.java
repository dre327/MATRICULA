package com.example.MATRICULA.app.seguridad.controller;

import com.example.MATRICULA.app.seguridad.facade.RolFacade;
import com.example.MATRICULA.dto.rol.RolRequest;
import com.example.MATRICULA.dto.rol.RolResponse;
import com.example.MATRICULA.security.RequierePermiso;
import com.example.MATRICULA.security.TipoAccion;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RolController {

    private final RolFacade facade;

    @GetMapping
    @RequierePermiso(modulo = "USUARIOS", accion = TipoAccion.VER)
    public List<RolResponse> listar() {
        return facade.listar();
    }

    @GetMapping("/{idRol}")
    @RequierePermiso(modulo = "USUARIOS", accion = TipoAccion.VER)
    public RolResponse obtener(@PathVariable Integer idRol) {
        return facade.obtener(idRol);
    }

    @PostMapping
    @RequierePermiso(modulo = "USUARIOS", accion = TipoAccion.INSERTAR)
    public RolResponse crear(@Valid @RequestBody RolRequest req) {
        return facade.crear(req);
    }

    @PutMapping("/{idRol}")
    @RequierePermiso(modulo = "USUARIOS", accion = TipoAccion.EDITAR)
    public RolResponse actualizar(@PathVariable Integer idRol,
                                  @Valid @RequestBody RolRequest req) {
        return facade.actualizar(idRol, req);
    }

    @DeleteMapping("/{idRol}")
    @RequierePermiso(modulo = "USUARIOS", accion = TipoAccion.ELIMINAR)
    public ResponseEntity<Void> eliminar(@PathVariable Integer idRol) {
        facade.eliminar(idRol);
        return ResponseEntity.noContent().build();
    }
}
