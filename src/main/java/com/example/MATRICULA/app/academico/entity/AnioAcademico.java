package com.example.MATRICULA.app.academico.entity;

import com.example.MATRICULA.common.entity.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "anio_academico")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class AnioAcademico extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_anio")
    private Integer idAnio;

    @Column(nullable = false, unique = true)
    private Integer anio;

    @Column(length = 100)
    private String descripcion;
}
