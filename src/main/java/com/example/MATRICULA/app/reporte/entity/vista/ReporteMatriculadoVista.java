package com.example.MATRICULA.app.reporte.entity.vista;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.time.LocalDate;

@Entity
@Immutable
@Table(name = "vw_reporte_matriculado")
@Getter
@Setter
public class ReporteMatriculadoVista {

    @Id
    private Integer id;

    @Column(name = "id_anio")
    private Integer idAnio;

    private String alumno;

    private String documento;

    private String nivel;

    private Integer grado;

    private String seccion;

    private LocalDate fecha;
}
