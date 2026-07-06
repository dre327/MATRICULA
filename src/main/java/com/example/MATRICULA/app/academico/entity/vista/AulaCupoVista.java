package com.example.MATRICULA.app.academico.entity.vista;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "vw_aula_cupo")
@Getter
@Setter
public class AulaCupoVista {

    @Id
    @Column(name = "id_aula")
    private Integer idAula;

    @Column(name = "id_anio")
    private Integer idAnio;

    @Column(name = "id_nivel")
    private Integer idNivel;

    private String nivel;

    private Integer grado;

    private String seccion;

    private Integer capacidad;

    private Integer ocupados;
}
