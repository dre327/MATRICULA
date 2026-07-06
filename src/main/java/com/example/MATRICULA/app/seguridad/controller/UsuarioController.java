package com.example.MATRICULA.app.seguridad.controller;

import com.example.MATRICULA.app.seguridad.facade.UsuarioFacade;
import com.example.MATRICULA.dto.usuario.CambiarPasswordRequest;
import com.example.MATRICULA.dto.usuario.UsuarioCreateRequest;
import com.example.MATRICULA.dto.usuario.UsuarioResponse;
import com.example.MATRICULA.dto.usuario.UsuarioUpdateRequest;
import com.example.MATRICULA.security.RequierePermiso;
import com.example.MATRICULA.security.SessionContext;
import com.example.MATRICULA.security.TipoAccion;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioFacade facade;
    private final SessionContext session;

    @GetMapping
    @RequierePermiso(modulo = "USUARIOS", accion = TipoAccion.VER)
    public List<UsuarioResponse> listar() {
        return facade.listar();
    }

    @GetMapping("/{idUsuario}")
    @RequierePermiso(modulo = "USUARIOS", accion = TipoAccion.VER)
    public UsuarioResponse obtener(@PathVariable Integer idUsuario) {
        return facade.obtener(idUsuario);
    }

    @PostMapping
    @RequierePermiso(modulo = "USUARIOS", accion = TipoAccion.INSERTAR)
    public UsuarioResponse crear(@Valid @RequestBody UsuarioCreateRequest req) {
        return facade.crear(req);
    }

    @PutMapping("/{idUsuario}")
    @RequierePermiso(modulo = "USUARIOS", accion = TipoAccion.EDITAR)
    public UsuarioResponse actualizar(@PathVariable Integer idUsuario,
                                      @Valid @RequestBody UsuarioUpdateRequest req) {
        return facade.actualizar(idUsuario, req);
    }

    @DeleteMapping("/{idUsuario}")
    @RequierePermiso(modulo = "USUARIOS", accion = TipoAccion.ELIMINAR)
    public ResponseEntity<Void> eliminar(@PathVariable Integer idUsuario) {
        facade.eliminar(idUsuario);
        return ResponseEntity.noContent().build();
    }

    /**
     * Self-service: cada usuario cambia SU propia contraseña.
     * No requiere @RequierePermiso — usa SessionContext para saber el idUsuario.
     */
    @PostMapping("/me/cambiar-password")
    public ResponseEntity<Void> cambiarPassword(@Valid @RequestBody CambiarPasswordRequest req) {
        facade.cambiarPassword(session.idUsuario(), req);
        return ResponseEntity.noContent().build();
    }
}
