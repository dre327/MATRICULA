package com.example.MATRICULA.app.reporte.entity.vista;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

@Entity
@Immutable
@Table(name = "vw_reporte_deuda")
@Getter
@Setter
public class ReporteDeudaVista {

    @Id
    private Integer id;

    @Column(name = "id_anio")
    private Integer idAnio;

    @Column(name = "id_alumno")
    private Integer idAlumno;

    private String alumno;

    private String documento;

    private String concepto;

    private BigDecimal monto;

    private Integer orden;
}
