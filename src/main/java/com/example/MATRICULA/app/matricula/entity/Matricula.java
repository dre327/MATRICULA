package com.example.MATRICULA.app.matricula.entity;

import com.example.MATRICULA.app.academico.entity.Alumno;
import com.example.MATRICULA.app.academico.entity.AnioAcademico;
import com.example.MATRICULA.app.academico.entity.Aula;
import com.example.MATRICULA.common.entity.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(
        name = "matricula",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_matricula",
                columnNames = {"id_alumno", "id_anio"}))
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Matricula extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_matricula")
    private Integer idMatricula;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_anio", nullable = false)
    private AnioAcademico anioAcademico;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_aula", nullable = false)
    private Aula aula;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_alumno", nullable = false)
    private Alumno alumno;

    @Column(name = "fec_matricula")
    private LocalDate fecMatricula;

    @Version
    private Long version;

    @PrePersist
    private void matriculaOnCreate() {
        if (fecMatricula == null) fecMatricula = LocalDate.now();
    }
}
