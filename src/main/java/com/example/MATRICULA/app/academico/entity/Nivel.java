package com.example.MATRICULA.app.academico.entity;

import com.example.MATRICULA.common.entity.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "nivel")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Nivel extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nivel")
    private Integer idNivel;

    @Column(nullable = false, length = 50)
    private String nombre;
}
