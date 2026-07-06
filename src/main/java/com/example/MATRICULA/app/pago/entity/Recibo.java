package com.example.MATRICULA.app.pago.entity;

import com.example.MATRICULA.app.matricula.entity.Cuota;
import com.example.MATRICULA.common.entity.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "recibo")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Recibo extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recibo")
    private Integer idRecibo;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_cuota", nullable = false, unique = true)
    private Cuota cuota;

    @Column(name = "nro_boleta", nullable = false, unique = true, length = 20)
    private String nroBoleta;

    @Column(name = "monto_pagado", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoPagado;

    @Column(name = "fec_pago")
    private LocalDate fecPago;

    @Column(length = 200)
    private String observacion;

    @PrePersist
    private void reciboOnCreate() {
        if (fecPago == null) fecPago = LocalDate.now();
    }
}
