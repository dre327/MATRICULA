package com.example.MATRICULA.app.reporte.controller;

import com.example.MATRICULA.app.reporte.facade.ReporteFacade;
import com.example.MATRICULA.dto.reporte.ReporteDeudaResponse;
import com.example.MATRICULA.dto.reporte.ReporteMatriculadoResponse;
import com.example.MATRICULA.dto.reporte.ReportePagoResponse;
import com.example.MATRICULA.security.RequierePermiso;
import com.example.MATRICULA.security.TipoAccion;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteFacade facade;

    @GetMapping("/matriculados")
    @RequierePermiso(modulo = "REPORTES", accion = TipoAccion.VER)
    public List<ReporteMatriculadoResponse> matriculados(@RequestParam Integer idAnio) {
        return facade.matriculadosPorAnio(idAnio);
    }

    @GetMapping("/pagos")
    @RequierePermiso(modulo = "REPORTES", accion = TipoAccion.VER)
    public List<ReportePagoResponse> pagos(@RequestParam Integer idAnio) {
        return facade.pagosPorAnio(idAnio);
    }

    @GetMapping("/deudas")
    @RequierePermiso(modulo = "REPORTES", accion = TipoAccion.VER)
    public List<ReporteDeudaResponse> deudas(
            @RequestParam Integer idAnio,
            @RequestParam(required = false) Integer idAlumno) {
        return idAlumno != null
                ? facade.deudasDeAlumnoPorAnio(idAnio, idAlumno)
                : facade.deudasPorAnio(idAnio);
    }
}
