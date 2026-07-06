package com.example.MATRICULA.app.tarifario.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tipo_concepto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoConcepto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_conc")
    private Integer idTipoConc;

    @Column(nullable = false, length = 50)
    private String descripcion;

    @Column(nullable = false)
    private Boolean estado;

    /**
     * Si es true, al crear un concepto de este tipo se generan
     * automáticamente {@link #cantidadCuotas} conceptos, uno por mes escolar
     * (MENSUALIDAD MARZO, MENSUALIDAD ABRIL, ...).
     */
    @Column(name = "es_recurrente", nullable = false)
    private Boolean esRecurrente;

    /**
     * Cuántos conceptos se crean cuando {@link #esRecurrente} es true.
     * Default: 1 (no recurrente).
     */
    @Column(name = "cantidad_cuotas", nullable = false)
    private Integer cantidadCuotas;
}
