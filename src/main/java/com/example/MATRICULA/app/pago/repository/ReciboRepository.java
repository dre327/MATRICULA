package com.example.MATRICULA.app.pago.repository;

import com.example.MATRICULA.app.pago.entity.Recibo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ReciboRepository extends JpaRepository<Recibo, Integer>, JpaSpecificationExecutor<Recibo> {

    Optional<Recibo> findByNroBoleta(String nroBoleta);

    Optional<Recibo> findByCuotaIdCuota(Integer idCuota);
}
