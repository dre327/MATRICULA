package com.example.MATRICULA.app.auditoria.entity.vista;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;

@Entity
@Immutable
@Table(name = "vw_auditoria_detalle")
@Getter
@Setter
public class AuditoriaVista {

    @Id
    private Long id;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    private String usuario;

    @Column(name = "nombre_completo")
    private String nombreCompleto;

    private String rol;

    private String modulo;

    @Column(name = "tabla_afectada")
    private String tablaAfectada;

    private String operacion;

    @Column(name = "codigo_registro")
    private Integer codigoRegistro;

    @Column(name = "ip_origen")
    private String ipOrigen;

    private String equipo;

    private String navegador;
}
