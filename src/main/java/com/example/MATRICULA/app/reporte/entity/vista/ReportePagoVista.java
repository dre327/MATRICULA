package com.example.MATRICULA.app.reporte.entity.vista;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Immutable
@Table(name = "vw_reporte_pago")
@Getter
@Setter
public class ReportePagoVista {

    @Id
    private Integer id;

    @Column(name = "id_anio")
    private Integer idAnio;

    private String boleta;

    private String alumno;

    private String documento;

    private String concepto;

    private BigDecimal monto;

    private LocalDate fecha;
}
