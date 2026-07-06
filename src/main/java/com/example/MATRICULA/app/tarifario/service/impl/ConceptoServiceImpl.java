package com.example.MATRICULA.app.tarifario.service.impl;

import com.example.MATRICULA.app.academico.entity.AnioAcademico;
import com.example.MATRICULA.app.academico.service.AnioAcademicoService;
import com.example.MATRICULA.app.tarifario.entity.Concepto;
import com.example.MATRICULA.app.tarifario.entity.TipoConcepto;
import com.example.MATRICULA.app.tarifario.repository.ConceptoRepository;
import com.example.MATRICULA.app.tarifario.service.ConceptoService;
import com.example.MATRICULA.app.tarifario.service.TipoConceptoService;
import com.example.MATRICULA.common.Constantes;
import com.example.MATRICULA.common.exception.NegocioException;
import com.example.MATRICULA.common.exception.RecursoNoEncontradoException;
import com.example.MATRICULA.common.service.BaseServiceImpl;
import com.example.MATRICULA.dto.concepto.ConceptoCreateRequest;
import com.example.MATRICULA.dto.concepto.ConceptoUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ConceptoServiceImpl extends BaseServiceImpl implements ConceptoService {

    private final ConceptoRepository repo;
    private final com.example.MATRICULA.app.matricula.repository.CuotaRepository cuotaRepo;
    private final AnioAcademicoService anioService;
    private final TipoConceptoService tipoConceptoService;

    @Override
    @Transactional(readOnly = true)
    public List<Concepto> listarPorAnio(Integer idAnio) {
        return repo.findByAnioAcademicoIdAnioAndEstadoTrueOrderByOrden(idAnio);
    }

    @Override
    @Transactional(readOnly = true)
    public Concepto obtener(Integer idConcepto) {
        return repo.findById(idConcepto)
                .orElseThrow(() -> new RecursoNoEncontradoException("Concepto", idConcepto));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Concepto> crear(ConceptoCreateRequest req) {
        AnioAcademico anio = anioService.obtener(req.getIdAnio());
        TipoConcepto tipo  = tipoConceptoService.obtener(req.getIdTipoConcepto());

        // Punto de partida para el orden (MAX+1 del año, considerando todos los activos)
        Integer max = repo.maxOrdenByAnio(req.getIdAnio());
        int siguienteOrden = (max == null ? 0 : max) + 1;

        List<Concepto> resultado = new ArrayList<>();

        if (Boolean.TRUE.equals(tipo.getEsRecurrente())
                && tipo.getCantidadCuotas() != null
                && tipo.getCantidadCuotas() > 1) {

            // ── Recurrente: generar N conceptos con sufijo de mes ────
            // Si alguno de los nombres ya existe activo, es porque no se
            // eliminó la tanda anterior. El usuario debe borrarla primero.
            int n = Math.min(tipo.getCantidadCuotas(), Constantes.MESES_ESCOLARES.length);
            for (int i = 0; i < n; i++) {
                String suffix = Constantes.MESES_ESCOLARES[i];
                String desc = (tipo.getDescripcion() + " " + suffix).toUpperCase();

                if (repo.existsByAnioAcademicoIdAnioAndDescripcionIgnoreCaseAndEstadoTrue(
                        req.getIdAnio(), desc))
                    throw new NegocioException(
                            "Ya existe '" + desc + "' activo para el año " + anio.getAnio() +
                                    ". Elimine la tanda anterior antes de generar otra.");

                Concepto c = construirConcepto(anio, tipo, desc, req.getMonto(),
                        siguienteOrden + i, req.getEsObligatorio());
                resultado.add(repo.save(c));
            }
        } else {
            // ── Único: exige descripción explícita y única ───────────
            if (req.getDescripcion() == null || req.getDescripcion().isBlank())
                throw new NegocioException(
                        "La descripción es obligatoria para conceptos del tipo " + tipo.getDescripcion());

            String desc = req.getDescripcion().trim();
            if (repo.existsByAnioAcademicoIdAnioAndDescripcionIgnoreCaseAndEstadoTrue(
                    req.getIdAnio(), desc))
                throw new NegocioException(
                        "Ya existe un concepto activo '" + desc + "' para el año " +
                                anio.getAnio() + ". Use un nombre distinto o edite el existente.");

            Integer orden = req.getOrden() != null ? req.getOrden() : siguienteOrden;
            Concepto c = construirConcepto(anio, tipo, desc, req.getMonto(),
                    orden, req.getEsObligatorio());
            resultado.add(repo.save(c));
        }

        auditar(Constantes.MODULO_CONCEPTOS, "concepto", Constantes.OP_INSERT,
                null, null,
                Map.of("idAnio", req.getIdAnio(),
                       "tipo",   tipo.getDescripcion(),
                       "cantidad", resultado.size(),
                       "ids", resultado.stream().map(Concepto::getIdConcepto).toList()));

        return resultado;
    }

    private Concepto construirConcepto(AnioAcademico anio, TipoConcepto tipo,
                                       String descripcion, java.math.BigDecimal monto,
                                       Integer orden, Boolean esObligatorio) {
        return Concepto.builder()
                .anioAcademico(anio)
                .tipoConcepto(tipo)
                .descripcion(descripcion)
                .monto(monto)
                .orden(orden)
                .esObligatorio(esObligatorio)
                .estado(Boolean.TRUE)
                .usuInsert(session.username())
                .build();
    }

    /**
     * Es el ejemplo textual del documento sobre Optimistic Lock:
     * "Si un usuario abre un concepto con precio de 200 y otro modifica a 250
     *  antes de guardar, el sistema deberá detectar el cambio y mostrar un mensaje..."
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Concepto actualizar(Integer idConcepto, ConceptoUpdateRequest req) {
        Concepto existe = obtener(idConcepto);

        // Chequeo explícito de versión — mensaje más claro que dejarlo a Hibernate
        if (!existe.getVersion().equals(req.getVersion()))
            throw new ObjectOptimisticLockingFailureException(Concepto.class, idConcepto);

        Object antes = snapshot(existe);
        existe.setDescripcion(req.getDescripcion().trim());
        existe.setMonto(req.getMonto());
        existe.setOrden(req.getOrden());
        existe.setEsObligatorio(req.getEsObligatorio());
        existe.setUsuUpdate(session.username());
        Concepto guardado = repo.save(existe);

        auditar(Constantes.MODULO_CONCEPTOS, "concepto", Constantes.OP_UPDATE,
                idConcepto, antes, snapshot(guardado));
        return guardado;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void eliminar(Integer idConcepto) {
        Concepto existe = obtener(idConcepto);
        Object antes = snapshot(existe);
        // Rename para liberar el UK (id_anio, descripcion) — así el admin puede
        // crear un nuevo concepto con la misma descripción sin choque.
        existe.setDescripcion(existe.getDescripcion() + " [ID:" + existe.getIdConcepto() + "]");
        existe.setEstado(Boolean.FALSE);
        existe.setUsuUpdate(session.username());
        repo.save(existe);

        // Anular cuotas PENDIENTES que apuntan a este concepto (cascada limpia).
        // Las CANCELADAS (pagadas) quedan intactas para preservar historial.
        int anuladas = cuotaRepo.anularPendientesDeConcepto(
                idConcepto, session.username(), java.time.LocalDateTime.now());

        auditar(Constantes.MODULO_CONCEPTOS, "concepto", Constantes.OP_DELETE,
                idConcepto,
                antes,
                Map.of("cuotasPendientesAnuladas", anuladas));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Concepto> clonar(Integer idAnioOrigen, Integer idAnioDestino) {
        AnioAcademico anioDestino = anioService.obtener(idAnioDestino);

        List<Concepto> origen = listarPorAnio(idAnioOrigen);
        if (origen.isEmpty())
            throw new NegocioException("El año origen no tiene conceptos para clonar");

        long yaExisten = repo.countByAnioAcademicoIdAnioAndEstadoTrue(idAnioDestino);
        if (yaExisten > 0)
            throw new NegocioException("El año destino ya tiene conceptos — no se puede clonar sobre existentes");

        List<Concepto> clonados = origen.stream()
                .<Concepto>map(c -> Concepto.builder()
                        .anioAcademico(anioDestino)
                        .tipoConcepto(c.getTipoConcepto())
                        .descripcion(c.getDescripcion())
                        .monto(c.getMonto())
                        .orden(c.getOrden())
                        .esObligatorio(c.getEsObligatorio())
                        .estado(Boolean.TRUE)
                        .usuInsert(session.username())
                        .build())
                .toList();
        List<Concepto> guardados = repo.saveAll(clonados);

        auditar(Constantes.MODULO_CONCEPTOS, "concepto", Constantes.OP_CLONAR_CONCEPTOS,
                idAnioDestino, null,
                Map.of("idAnioOrigen", idAnioOrigen,
                       "idAnioDestino", idAnioDestino,
                       "cantidadClonada", guardados.size()));
        return guardados;
    }

    private Map<String, Object> snapshot(Concepto c) {
        Map<String, Object> m = new java.util.LinkedHashMap<>();
        m.put("idConcepto",   c.getIdConcepto());
        m.put("idAnio",       c.getAnioAcademico() != null ? c.getAnioAcademico().getIdAnio() : null);
        m.put("idTipoConc",   c.getTipoConcepto() != null ? c.getTipoConcepto().getIdTipoConc() : null);
        m.put("descripcion",  c.getDescripcion());
        m.put("monto",        c.getMonto());
        m.put("orden",        c.getOrden());
        m.put("esObligatorio", c.getEsObligatorio());
        m.put("estado",       c.getEstado());
        m.put("version",      c.getVersion());
        return m;
    }
}
