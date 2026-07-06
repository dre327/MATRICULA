package com.example.MATRICULA.app.academico.entity;

import com.example.MATRICULA.common.entity.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(
        name = "aula",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_aula",
                columnNames = {"id_anio", "id_nivel", "grado", "seccion"}))
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Aula extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aula")
    private Integer idAula;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_anio", nullable = false)
    private AnioAcademico anioAcademico;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_nivel", nullable = false)
    private Nivel nivel;

    @Column(nullable = false)
    private Integer grado;

    @Column(nullable = false, length = 5)
    private String seccion;

    @Version
    private Long version;
}
