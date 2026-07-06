package com.example.MATRICULA.app.matricula.entity;

import com.example.MATRICULA.app.tarifario.entity.Concepto;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Cuota — no extiende AuditableEntity porque no tiene columna 'estado':
 * su ciclo de vida se controla con 'estado_pago' (P=Pendiente, C=Cancelado, A=Anulado).
 * Los campos de auditoría se declaran inline.
 */
@Entity
@Table(name = "cuota")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cuota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cuota")
    private Integer idCuota;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_matricula", nullable = false)
    private Matricula matricula;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_concepto", nullable = false)
    private Concepto concepto;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    /** P=Pendiente, C=Cancelado, A=Anulado */
    @Column(name = "estado_pago", nullable = false, length = 1)
    private Character estadoPago;

    @Version
    private Long version;

    @Column(name = "usu_insert", length = 50)
    private String usuInsert;

    @Column(name = "fec_insert")
    private LocalDateTime fecInsert;

    @Column(name = "usu_update", length = 50)
    private String usuUpdate;

    @Column(name = "fec_update")
    private LocalDateTime fecUpdate;

    @PrePersist
    private void onCreate() {
        fecInsert = LocalDateTime.now();
        if (estadoPago == null) estadoPago = 'P';
    }

    @PreUpdate
    private void onUpdate() {
        fecUpdate = LocalDateTime.now();
    }
}
