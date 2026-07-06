package com.example.MATRICULA.app.seguridad.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "funcionalidad")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Funcionalidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_funcionalidad")
    private Integer idFuncionalidad;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 150)
    private String url;

    @Column(length = 30)
    private String icono;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_padre")
    private Funcionalidad padre;

    private Integer orden;

    @Column(nullable = false)
    private Boolean estado;
}
