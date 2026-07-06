package com.example.MATRICULA.app.academico.controller;

import com.example.MATRICULA.app.academico.facade.AulaFacade;
import com.example.MATRICULA.dto.aula.AulaCreateRequest;
import com.example.MATRICULA.dto.aula.AulaCupoResponse;
import com.example.MATRICULA.dto.aula.AulaResponse;
import com.example.MATRICULA.dto.aula.AulaUpdateRequest;
import com.example.MATRICULA.security.RequierePermiso;
import com.example.MATRICULA.security.TipoAccion;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aulas")
@RequiredArgsConstructor
public class AulaController {

    private final AulaFacade facade;

    @GetMapping
    @RequierePermiso(modulo = "AULAS", accion = TipoAccion.VER)
    public List<AulaResponse> listarPorAnio(@RequestParam Integer idAnio) {
        return facade.listarPorAnio(idAnio);
    }

    /**
     * Vista de cupos por aula: aula + capacidad + ocupados.
     * Se usa en el modal de selección de aula al matricular
     * para saber cuáles tienen vacantes.
     */
    @GetMapping("/cupos")
    @RequierePermiso(modulo = "AULAS", accion = TipoAccion.VER)
    public List<AulaCupoResponse> listarCupos(@RequestParam Integer idAnio) {
        return facade.listarCuposPorAnio(idAnio);
    }

    @GetMapping("/{idAula}")
    @RequierePermiso(modulo = "AULAS", accion = TipoAccion.VER)
    public AulaResponse obtener(@PathVariable Integer idAula) {
        return facade.obtener(idAula);
    }

    @PostMapping
    @RequierePermiso(modulo = "AULAS", accion = TipoAccion.INSERTAR)
    public AulaResponse crear(@Valid @RequestBody AulaCreateRequest req) {
        return facade.crear(req);
    }

    @PutMapping("/{idAula}")
    @RequierePermiso(modulo = "AULAS", accion = TipoAccion.EDITAR)
    public AulaResponse actualizar(@PathVariable Integer idAula,
                                   @Valid @RequestBody AulaUpdateRequest req) {
        return facade.actualizar(idAula, req);
    }

    @DeleteMapping("/{idAula}")
    @RequierePermiso(modulo = "AULAS", accion = TipoAccion.ELIMINAR)
    public ResponseEntity<Void> eliminar(@PathVariable Integer idAula) {
        facade.eliminar(idAula);
        return ResponseEntity.noContent().build();
    }
}
