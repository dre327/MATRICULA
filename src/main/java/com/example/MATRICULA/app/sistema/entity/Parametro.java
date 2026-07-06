package com.example.MATRICULA.app.sistema.entity;

import com.example.MATRICULA.common.entity.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "parametro")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Parametro extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_parametro")
    private Integer idParametro;

    @Column(nullable = false, unique = true, length = 50)
    private String clave;

    @Column(nullable = false, length = 100)
    private String valor;

    @Column(length = 150)
    private String descripcion;
}
