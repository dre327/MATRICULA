package com.example.MATRICULA.app.academico.controller;

import com.example.MATRICULA.app.academico.facade.AlumnoFacade;
import com.example.MATRICULA.dto.alumno.AlumnoCreateRequest;
import com.example.MATRICULA.dto.alumno.AlumnoFiltroRequest;
import com.example.MATRICULA.dto.alumno.AlumnoResponse;
import com.example.MATRICULA.dto.alumno.AlumnoUpdateRequest;
import com.example.MATRICULA.dto.comun.PageResponse;
import com.example.MATRICULA.security.RequierePermiso;
import com.example.MATRICULA.security.TipoAccion;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alumnos")
@RequiredArgsConstructor
public class AlumnoController {

    private final AlumnoFacade facade;

    /**
     * Búsqueda paginada con filtros opcionales.
     * Los filtros se reciben como query params (?nombre=juan&pagina=0&tamano=20).
     */
    @GetMapping
    @RequierePermiso(modulo = "ALUMNOS", accion = TipoAccion.VER)
    public PageResponse<AlumnoResponse> buscar(@ModelAttribute AlumnoFiltroRequest filtro) {
        return facade.buscar(filtro);
    }

    @GetMapping("/{idAlumno}")
    @RequierePermiso(modulo = "ALUMNOS", accion = TipoAccion.VER)
    public AlumnoResponse obtener(@PathVariable Integer idAlumno) {
        return facade.obtener(idAlumno);
    }

    @PostMapping
    @RequierePermiso(modulo = "ALUMNOS", accion = TipoAccion.INSERTAR)
    public AlumnoResponse crear(@Valid @RequestBody AlumnoCreateRequest req) {
        return facade.crear(req);
    }

    @PutMapping("/{idAlumno}")
    @RequierePermiso(modulo = "ALUMNOS", accion = TipoAccion.EDITAR)
    public AlumnoResponse actualizar(@PathVariable Integer idAlumno,
                                     @Valid @RequestBody AlumnoUpdateRequest req) {
        return facade.actualizar(idAlumno, req);
    }

    @DeleteMapping("/{idAlumno}")
    @RequierePermiso(modulo = "ALUMNOS", accion = TipoAccion.ELIMINAR)
    public ResponseEntity<Void> eliminar(@PathVariable Integer idAlumno) {
        facade.eliminar(idAlumno);
        return ResponseEntity.noContent().build();
    }
}
