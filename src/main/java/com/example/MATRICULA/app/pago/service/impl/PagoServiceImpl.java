package com.example.MATRICULA.app.pago.service.impl;

import com.example.MATRICULA.app.matricula.entity.Cuota;
import com.example.MATRICULA.app.matricula.entity.Matricula;
import com.example.MATRICULA.app.matricula.repository.CuotaRepository;
import com.example.MATRICULA.app.matricula.service.MatriculaService;
import com.example.MATRICULA.app.pago.entity.Recibo;
import com.example.MATRICULA.app.pago.repository.ReciboRepository;
import com.example.MATRICULA.app.pago.service.PagoService;
import com.example.MATRICULA.app.sistema.entity.Correlativo;
import com.example.MATRICULA.app.sistema.repository.CorrelativoRepository;
import com.example.MATRICULA.common.Constantes;
import com.example.MATRICULA.common.enums.EstadoPago;
import com.example.MATRICULA.common.exception.NegocioException;
import com.example.MATRICULA.common.exception.RecursoNoEncontradoException;
import com.example.MATRICULA.common.service.BaseServiceImpl;
import com.example.MATRICULA.dto.pago.PagarRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PagoServiceImpl extends BaseServiceImpl implements PagoService {

    private final CuotaRepository cuotaRepo;
    private final ReciboRepository reciboRepo;
    private final CorrelativoRepository correlativoRepo;
    private final MatriculaService matriculaService;

    @Override
    @Transactional(readOnly = true)
    public List<CuotaConEstado> listarDeudas(Integer idAlumno, Integer idAnio) {
        Matricula matricula = matriculaService.obtenerPorAlumnoYAnio(idAlumno, idAnio);
        List<Cuota> cuotas = cuotaRepo.findByMatriculaIdMatriculaOrderByConceptoOrden(
                matricula.getIdMatricula());

        // Reglas de "pagable":
        //   1. Cuota MATRICULA pendiente: siempre pagable (gatekeeper).
        //   2. Si TODAS las cuotas MATRICULA están canceladas:
        //      - Cuotas recurrentes (MENSUALIDAD): pagable solo la primera pendiente de la cadena.
        //      - Cuotas no recurrentes no-matricula (UNIFORME, MATERIAL): pagable si están pendientes.
        //   3. Si la matricula está pendiente: NADA MÁS es pagable.
        //   4. Canceladas / anuladas: nunca pagables.

        boolean matriculaTotalmentePagada = cuotas.stream()
                .filter(c -> esTipoMatricula(c))
                .allMatch(c -> EstadoPago.CANCELADO.getCodigo().equals(c.getEstadoPago()));

        boolean primeraRecurrentePendienteEncontrada = false;
        List<CuotaConEstado> result = new java.util.ArrayList<>(cuotas.size());
        for (Cuota c : cuotas) {
            boolean pendiente = EstadoPago.PENDIENTE.getCodigo().equals(c.getEstadoPago());
            boolean esMatricula = esTipoMatricula(c);
            boolean esRecurrente = Boolean.TRUE.equals(c.getConcepto().getTipoConcepto().getEsRecurrente());

            boolean pagable = false;
            if (pendiente) {
                if (esMatricula) {
                    pagable = true;
                } else if (matriculaTotalmentePagada) {
                    if (esRecurrente) {
                        if (!primeraRecurrentePendienteEncontrada) {
                            pagable = true;
                            primeraRecurrentePendienteEncontrada = true;
                        }
                    } else {
                        // Único no-matrícula (UNIFORME, MATERIAL): habilitado en paralelo
                        pagable = true;
                    }
                }
                // else: matrícula sin pagar → nada más es pagable
            }
            result.add(new CuotaConEstado(c, pagable));
        }
        return result;
    }

    /** ¿La cuota corresponde a un concepto de tipo MATRICULA? */
    private boolean esTipoMatricula(Cuota c) {
        return c.getConcepto() != null
                && c.getConcepto().getTipoConcepto() != null
                && Constantes.TIPO_MATRICULA.equalsIgnoreCase(
                        c.getConcepto().getTipoConcepto().getDescripcion());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Recibo pagar(PagarRequest req) {
        // ── Cargar cuota ────────────────────────────────────────────
        Cuota cuota = cuotaRepo.findById(req.getIdCuota())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cuota", req.getIdCuota()));

        Matricula matricula = matriculaService.obtenerPorAlumnoYAnio(
                req.getIdAlumno(), req.getIdAnio());
        if (!cuota.getMatricula().getIdMatricula().equals(matricula.getIdMatricula()))
            throw new NegocioException("La cuota no corresponde al alumno indicado");

        // ── Validación 1: cuota no cancelada previamente ───────────
        if (EstadoPago.CANCELADO.getCodigo().equals(cuota.getEstadoPago()))
            throw new NegocioException("Esta cuota ya fue cancelada anteriormente");
        if (EstadoPago.ANULADO.getCodigo().equals(cuota.getEstadoPago()))
            throw new NegocioException("Esta cuota está anulada — no se puede pagar");

        // ── Validación 2: reglas de orden de pago (Modelo C) ───────
        boolean esMatricula = esTipoMatricula(cuota);
        boolean esRecurrente = Boolean.TRUE.equals(cuota.getConcepto().getTipoConcepto().getEsRecurrente());

        if (!esMatricula) {
            // 2a. La matrícula tiene que estar totalmente pagada
            List<Cuota> todasLasCuotas = cuotaRepo.findByMatriculaIdMatriculaOrderByConceptoOrden(
                    matricula.getIdMatricula());
            boolean matriculaPagada = todasLasCuotas.stream()
                    .filter(this::esTipoMatricula)
                    .allMatch(mc -> EstadoPago.CANCELADO.getCodigo().equals(mc.getEstadoPago()));
            if (!matriculaPagada)
                throw new NegocioException(
                        "Debe pagar la matrícula antes de pagar cualquier otro concepto.");

            // 2b. Si es recurrente (mensualidad), respetar la cadena por orden
            if (esRecurrente) {
                boolean hayRecurrenteAnteriorPendiente = todasLasCuotas.stream()
                        .filter(oc -> Boolean.TRUE.equals(oc.getConcepto().getTipoConcepto().getEsRecurrente()))
                        .filter(oc -> oc.getConcepto().getOrden() < cuota.getConcepto().getOrden())
                        .anyMatch(oc -> EstadoPago.PENDIENTE.getCodigo().equals(oc.getEstadoPago()));
                if (hayRecurrenteAnteriorPendiente)
                    throw new NegocioException(
                            "Existen mensualidades anteriores pendientes. Debe pagarlas en orden.");
            }
            // 2c. Único no-matrícula (UNIFORME, MATERIAL): sin restricción adicional
        }

        // ── Paso 1: marcar cuota como cancelada ─────────────────────
        cuota.setEstadoPago(EstadoPago.CANCELADO.getCodigo());
        cuota.setUsuUpdate(session.username());
        cuotaRepo.save(cuota);

        // ── Paso 2: obtener siguiente número con SELECT FOR UPDATE ─
        //         (findById tiene @Lock PESSIMISTIC_WRITE en el repo)
        Correlativo correlativo = correlativoRepo.findById(Constantes.CLAVE_CORRELATIVO_BOLETA)
                .orElseThrow(() -> new NegocioException(
                        "No existe el correlativo " + Constantes.CLAVE_CORRELATIVO_BOLETA));
        int siguiente = correlativo.getUltimoValor() + 1;
        correlativo.setUltimoValor(siguiente);
        correlativoRepo.save(correlativo);
        String nroBoleta = String.format(Constantes.PREFIJO_BOLETA + "%06d", siguiente);

        // ── Paso 3: generar Recibo ──────────────────────────────────
        Recibo recibo = Recibo.builder()
                .cuota(cuota)
                .nroBoleta(nroBoleta)
                .montoPagado(cuota.getMonto())
                .observacion(req.getObservacion())
                .estado(Boolean.TRUE)
                .usuInsert(session.username())
                .build();
        recibo = reciboRepo.save(recibo);

        // ── Paso 4: auditoría ───────────────────────────────────────
        auditar(Constantes.MODULO_PAGOS, "recibo", Constantes.OP_PAGO,
                recibo.getIdRecibo(),
                null,
                Map.of("idRecibo",  recibo.getIdRecibo(),
                       "idCuota",   cuota.getIdCuota(),
                       "nroBoleta", nroBoleta,
                       "monto",     cuota.getMonto()));

        return recibo;
    }
}
