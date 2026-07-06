package com.example.MATRICULA.app.tarifario.entity;

import com.example.MATRICULA.app.academico.entity.AnioAcademico;
import com.example.MATRICULA.common.entity.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Table(
        name = "concepto",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_concepto",
                columnNames = {"id_anio", "descripcion"}))
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Concepto extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_concepto")
    private Integer idConcepto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_anio", nullable = false)
    private AnioAcademico anioAcademico;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_tipo_conc", nullable = false)
    private TipoConcepto tipoConcepto;

    @Column(nullable = false, length = 100)
    private String descripcion;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Column(nullable = false)
    private Integer orden;

    @Column(name = "es_obligatorio", nullable = false)
    private Boolean esObligatorio;

    @Version
    private Long version;
}
