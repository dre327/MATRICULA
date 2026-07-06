package com.example.MATRICULA.app.academico.controller;

import com.example.MATRICULA.app.academico.facade.AnioAcademicoFacade;
import com.example.MATRICULA.dto.anio.AnioAcademicoRequest;
import com.example.MATRICULA.dto.anio.AnioAcademicoResponse;
import com.example.MATRICULA.security.RequierePermiso;
import com.example.MATRICULA.security.TipoAccion;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/anios")
@RequiredArgsConstructor
public class AnioAcademicoController {

    private final AnioAcademicoFacade facade;

    @GetMapping
    @RequierePermiso(modulo = "ANIOS", accion = TipoAccion.VER)
    public List<AnioAcademicoResponse> listar() {
        return facade.listar();
    }

    @GetMapping("/{idAnio}")
    @RequierePermiso(modulo = "ANIOS", accion = TipoAccion.VER)
    public AnioAcademicoResponse obtener(@PathVariable Integer idAnio) {
        return facade.obtener(idAnio);
    }

    @PostMapping
    @RequierePermiso(modulo = "ANIOS", accion = TipoAccion.INSERTAR)
    public AnioAcademicoResponse crear(@Valid @RequestBody AnioAcademicoRequest req) {
        return facade.crear(req);
    }

    @DeleteMapping("/{idAnio}")
    @RequierePermiso(modulo = "ANIOS", accion = TipoAccion.ELIMINAR)
    public ResponseEntity<Void> eliminar(@PathVariable Integer idAnio) {
        facade.eliminar(idAnio);
        return ResponseEntity.noContent().build();
    }
}
