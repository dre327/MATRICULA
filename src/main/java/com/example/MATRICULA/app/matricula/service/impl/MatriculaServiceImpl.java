package com.example.MATRICULA.app.matricula.service.impl;

import com.example.MATRICULA.app.academico.entity.Alumno;
import com.example.MATRICULA.app.academico.entity.AnioAcademico;
import com.example.MATRICULA.app.academico.entity.Aula;
import com.example.MATRICULA.app.academico.entity.vista.AulaCupoVista;
import com.example.MATRICULA.app.academico.repository.vista.AulaCupoVistaRepository;
import com.example.MATRICULA.app.academico.service.AlumnoService;
import com.example.MATRICULA.app.academico.service.AnioAcademicoService;
import com.example.MATRICULA.app.academico.service.AulaService;
import com.example.MATRICULA.app.matricula.entity.Cuota;
import com.example.MATRICULA.app.matricula.entity.Matricula;
import com.example.MATRICULA.app.matricula.repository.CuotaRepository;
import com.example.MATRICULA.app.matricula.repository.MatriculaRepository;
import com.example.MATRICULA.app.matricula.service.MatriculaService;
import com.example.MATRICULA.app.tarifario.entity.Concepto;
import com.example.MATRICULA.app.tarifario.service.ConceptoService;
import com.example.MATRICULA.common.Constantes;
import com.example.MATRICULA.common.enums.EstadoPago;
import com.example.MATRICULA.common.exception.NegocioException;
import com.example.MATRICULA.common.exception.RecursoNoEncontradoException;
import com.example.MATRICULA.common.service.BaseServiceImpl;
import com.example.MATRICULA.dto.matricula.MatricularRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MatriculaServiceImpl extends BaseServiceImpl implements MatriculaService {

    private final MatriculaRepository matriculaRepo;
    private final CuotaRepository cuotaRepo;
    private final AulaCupoVistaRepository cupoRepo;
    private final AlumnoService alumnoService;
    private final AulaService aulaService;
    private final AnioAcademicoService anioService;
    private final ConceptoService conceptoService;

    /**
     * Flujo del documento:
     *
     *   Alumno → Registrar Matrícula → Generar Cuotas → Registrar Auditoría → Commit
     *
     *   Si ocurre cualquier error → Rollback completo.
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Matricula matricular(MatricularRequest req) {

        // ── Validación 1: alumno no matriculado ese año ─────────────
        if (matriculaRepo.existsByAlumnoIdAlumnoAndAnioAcademicoIdAnioAndEstadoTrue(
                req.getIdAlumno(), req.getIdAnio()))
            throw new NegocioException(
                    "El alumno ya tiene matrícula registrada en este año académico");

        // ── Cargar entidades ────────────────────────────────────────
        Alumno alumno = alumnoService.obtener(req.getIdAlumno());
        Aula aula = aulaService.obtener(req.getIdAula());
        AnioAcademico anio = anioService.obtener(req.getIdAnio());

        if (!anio.getIdAnio().equals(aula.getAnioAcademico().getIdAnio()))
            throw new NegocioException(
                    "El aula seleccionada no pertenece al año académico indicado");

        // ── Validación 2: aula con cupos ────────────────────────────
        AulaCupoVista cupo = cupoRepo.findById(req.getIdAula())
                .orElseThrow(() -> new RecursoNoEncontradoException("Aula", req.getIdAula()));
        if (cupo.getOcupados() >= cupo.getCapacidad())
            throw new NegocioException(
                    "El aula ya alcanzó su capacidad máxima de " + cupo.getCapacidad() + " alumnos");

        // ── Validación 3: hay conceptos activos ─────────────────────
        List<Concepto> conceptosActivos = conceptoService.listarPorAnio(req.getIdAnio());
        if (conceptosActivos.isEmpty())
            throw new NegocioException(
                    "No hay conceptos configurados para el año académico. " +
                            "Registre los conceptos antes de matricular.");

        // ── FILTRADO (Modelo C) ─────────────────────────────────────
        // Agrupamos por tipo:
        //   - Tipos únicos (MATRICULA, UNIFORME, MATERIAL): nos quedamos SOLO
        //     con el de mayor id_concepto (el más recientemente creado = "última versión").
        //   - Tipos recurrentes (MENSUALIDAD): nos quedamos con los últimos N por id_concepto
        //     donde N = tipo.cantidad_cuotas (los 10 más nuevos).
        List<Concepto> conceptosParaMatricula = seleccionarUltimaVersionPorTipo(conceptosActivos);

        // ── Paso 1: crear Matricula ─────────────────────────────────
        Matricula m = Matricula.builder()
                .anioAcademico(anio)
                .aula(aula)
                .alumno(alumno)
                .estado(Boolean.TRUE)
                .usuInsert(session.username())
                .build();
        m = matriculaRepo.save(m);
        final Matricula matriculaFinal = m;

        // ── Paso 2: generar Cuotas de la lista filtrada ─────────────
        List<Cuota> cuotas = conceptosParaMatricula.stream()
                .<Cuota>map(c -> Cuota.builder()
                        .matricula(matriculaFinal)
                        .concepto(c)
                        .monto(c.getMonto())
                        .estadoPago(EstadoPago.PENDIENTE.getCodigo())
                        .usuInsert(session.username())
                        .build())
                .toList();
        cuotaRepo.saveAll(cuotas);

        // ── Paso 3: auditoría (dentro de la misma transacción) ──────
        auditar(Constantes.MODULO_MATRICULA, "matricula", Constantes.OP_MATRICULA,
                m.getIdMatricula(),
                null,
                Map.of("idMatricula", m.getIdMatricula(),
                       "idAlumno",    alumno.getIdAlumno(),
                       "idAula",      aula.getIdAula(),
                       "idAnio",      anio.getIdAnio(),
                       "cuotasGeneradas", cuotas.size()));

        return m;
    }

    /**
     * De la lista de conceptos activos, devuelve solo la "última versión" por tipo:
     *   - Tipo NO recurrente → el de mayor id_concepto (solo 1).
     *   - Tipo recurrente    → los últimos N por id_concepto (con N = cantidad_cuotas).
     * El resultado sale ordenado por concepto.orden para respetar la secuencia de pago.
     */
    private List<Concepto> seleccionarUltimaVersionPorTipo(List<Concepto> conceptos) {
        // Agrupar por idTipoConc
        Map<Integer, List<Concepto>> porTipo = new java.util.LinkedHashMap<>();
        for (Concepto c : conceptos) {
            porTipo.computeIfAbsent(c.getTipoConcepto().getIdTipoConc(), k -> new java.util.ArrayList<>())
                   .add(c);
        }

        List<Concepto> filtrados = new java.util.ArrayList<>();
        for (List<Concepto> grupo : porTipo.values()) {
            // Ordenar por id_concepto descendente (más nuevo primero)
            grupo.sort((a, b) -> Integer.compare(b.getIdConcepto(), a.getIdConcepto()));
            com.example.MATRICULA.app.tarifario.entity.TipoConcepto tipo = grupo.get(0).getTipoConcepto();
            boolean esRecurrente = Boolean.TRUE.equals(tipo.getEsRecurrente())
                    && tipo.getCantidadCuotas() != null
                    && tipo.getCantidadCuotas() > 1;
            int n = esRecurrente ? tipo.getCantidadCuotas() : 1;
            for (int i = 0; i < Math.min(n, grupo.size()); i++) {
                filtrados.add(grupo.get(i));
            }
        }
        // Reordenar por concepto.orden para que las cuotas queden en secuencia de pago
        filtrados.sort((a, b) -> Integer.compare(a.getOrden(), b.getOrden()));
        return filtrados;
    }

    @Override
    @Transactional(readOnly = true)
    public Matricula obtenerPorAlumnoYAnio(Integer idAlumno, Integer idAnio) {
        return matriculaRepo
                .findByAlumnoIdAlumnoAndAnioAcademicoIdAnioAndEstadoTrue(idAlumno, idAnio)
                .orElseThrow(() -> new NegocioException(
                        "El alumno no tiene matrícula activa en el año seleccionado"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cuota> listarCuotasDeMatricula(Integer idMatricula) {
        return cuotaRepo.findByMatriculaIdMatriculaOrderByConceptoOrden(idMatricula);
    }
}
