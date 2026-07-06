package com.example.MATRICULA.app.academico.entity;

import com.example.MATRICULA.common.entity.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(
        name = "alumno",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_alumno_doc",
                columnNames = {"id_tipo_doc", "nro_documento"}))
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Alumno extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alumno")
    private Integer idAlumno;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_tipo_doc", nullable = false)
    private TipoDocumento tipoDocumento;

    @Column(name = "nro_documento", nullable = false, length = 20)
    private String nroDocumento;

    @Column(name = "ap_paterno", nullable = false, length = 60)
    private String apPaterno;

    @Column(name = "ap_materno", nullable = false, length = 60)
    private String apMaterno;

    @Column(nullable = false, length = 80)
    private String nombre;

    @Column(name = "fec_nacimiento")
    private LocalDate fecNacimiento;

    /** M = Masculino, F = Femenino */
    @Column(length = 1)
    private Character sexo;
}
