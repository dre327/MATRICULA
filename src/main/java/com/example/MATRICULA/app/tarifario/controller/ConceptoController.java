package com.example.MATRICULA.app.tarifario.controller;

import com.example.MATRICULA.app.tarifario.facade.ConceptoFacade;
import com.example.MATRICULA.dto.concepto.ClonarConceptosRequest;
import com.example.MATRICULA.dto.concepto.ConceptoCreateRequest;
import com.example.MATRICULA.dto.concepto.ConceptoResponse;
import com.example.MATRICULA.dto.concepto.ConceptoUpdateRequest;
import com.example.MATRICULA.security.RequierePermiso;
import com.example.MATRICULA.security.TipoAccion;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conceptos")
@RequiredArgsConstructor
public class ConceptoController {

    private final ConceptoFacade facade;

    @GetMapping
    @RequierePermiso(modulo = "CONCEPTOS", accion = TipoAccion.VER)
    public List<ConceptoResponse> listarPorAnio(@RequestParam Integer idAnio) {
        return facade.listarPorAnio(idAnio);
    }

    @GetMapping("/{idConcepto}")
    @RequierePermiso(modulo = "CONCEPTOS", accion = TipoAccion.VER)
    public ConceptoResponse obtener(@PathVariable Integer idConcepto) {
        return facade.obtener(idConcepto);
    }

    @PostMapping
    @RequierePermiso(modulo = "CONCEPTOS", accion = TipoAccion.INSERTAR)
    public List<ConceptoResponse> crear(@Valid @RequestBody ConceptoCreateRequest req) {
        return facade.crear(req);
    }

    @PutMapping("/{idConcepto}")
    @RequierePermiso(modulo = "CONCEPTOS", accion = TipoAccion.EDITAR)
    public ConceptoResponse actualizar(@PathVariable Integer idConcepto,
                                       @Valid @RequestBody ConceptoUpdateRequest req) {
        return facade.actualizar(idConcepto, req);
    }

    @DeleteMapping("/{idConcepto}")
    @RequierePermiso(modulo = "CONCEPTOS", accion = TipoAccion.ELIMINAR)
    public ResponseEntity<Void> eliminar(@PathVariable Integer idConcepto) {
        facade.eliminar(idConcepto);
        return ResponseEntity.noContent().build();
    }

    /**
     * Clona todos los conceptos activos del año origen al año destino.
     * Requiere permiso de INSERTAR — es una operación de creación masiva.
     */
    @PostMapping("/clonar")
    @RequierePermiso(modulo = "CONCEPTOS", accion = TipoAccion.INSERTAR)
    public List<ConceptoResponse> clonar(@Valid @RequestBody ClonarConceptosRequest req) {
        return facade.clonar(req);
    }
}
