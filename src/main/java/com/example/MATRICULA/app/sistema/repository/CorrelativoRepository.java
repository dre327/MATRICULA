package com.example.MATRICULA.app.sistema.repository;

import com.example.MATRICULA.app.sistema.entity.Correlativo;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface CorrelativoRepository extends JpaRepository<Correlativo, String> {

    /**
     * Sobrescribe findById para aplicar bloqueo pesimista (SELECT ... FOR UPDATE).
     * Debe llamarse SIEMPRE dentro de una transacción. Garantiza que dos pagos
     * concurrentes no obtengan el mismo número de boleta: el segundo espera
     * hasta que el primero haga commit.
     */
    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Correlativo> findById(String id);
}
