package com.example.MATRICULA.app.auditoria.entity;

import com.example.MATRICULA.app.seguridad.entity.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "auditoria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_auditoria")
    private Long idAuditoria;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(nullable = false, length = 50)
    private String modulo;

    @Column(name = "tabla_afectada", nullable = false, length = 50)
    private String tablaAfectada;

    @Column(nullable = false, length = 20)
    private String operacion;

    @Column(name = "codigo_registro")
    private Integer codigoRegistro;

    @Column(name = "valor_anterior", columnDefinition = "JSON")
    private String valorAnterior;

    @Column(name = "valor_nuevo", columnDefinition = "JSON")
    private String valorNuevo;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "ip_origen", length = 45)
    private String ipOrigen;

    @Column(length = 100)
    private String equipo;

    @Column(length = 150)
    private String navegador;

    @PrePersist
    protected void onCreate() {
        if (fechaHora == null) fechaHora = LocalDateTime.now();
    }
}
