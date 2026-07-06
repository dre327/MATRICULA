package com.example.MATRICULA.app.matricula.repository;

import com.example.MATRICULA.app.matricula.entity.Cuota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CuotaRepository extends JpaRepository<Cuota, Integer> {

    List<Cuota> findByMatriculaIdMatriculaOrderByConceptoOrden(Integer idMatricula);

    List<Cuota> findByMatriculaIdMatriculaAndEstadoPagoOrderByConceptoOrden(Integer idMatricula, Character estadoPago);

    /**
     * ¿Existe alguna cuota pendiente ('P') con orden estrictamente menor
     * al orden dado en la matrícula indicada? Se usa para bloquear el pago
     * de cuotas superiores cuando hay deudas anteriores.
     */
    boolean existsByMatriculaIdMatriculaAndConceptoOrdenLessThanAndEstadoPago(Integer idMatricula, Integer orden, Character estadoPago);

    /**
     * Anula (estadoPago = 'A') todas las cuotas PENDIENTES que apuntan al concepto dado.
     * Se usa cuando un concepto se desactiva/reemplaza — las deudas de matrículas
     * antiguas por ese concepto ya no aplican.
     * Las cuotas ya CANCELADAS (pagadas) NO se tocan — historial se preserva.
     *
     * @return cantidad de filas afectadas
     */
    @Modifying
    @Query("UPDATE Cuota c " +
           "   SET c.estadoPago = 'A', c.usuUpdate = :usuario, c.fecUpdate = :ahora " +
           " WHERE c.concepto.idConcepto = :idConcepto " +
           "   AND c.estadoPago = 'P'")
    int anularPendientesDeConcepto(@Param("idConcepto") Integer idConcepto,
                                   @Param("usuario") String usuario,
                                   @Param("ahora") LocalDateTime ahora);
}
