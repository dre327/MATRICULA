package com.example.MATRICULA.app.seguridad.entity;

import com.example.MATRICULA.common.entity.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(
        name = "rol_funcionalidad",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_rol_func",
                columnNames = {"id_rol", "id_funcionalidad"}))
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class RolFuncionalidad extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol_funcionalidad")
    private Integer idRolFuncionalidad;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_funcionalidad", nullable = false)
    private Funcionalidad funcionalidad;

    @Column(name = "puede_ver", nullable = false)
    private Boolean puedeVer;

    @Column(name = "puede_insertar", nullable = false)
    private Boolean puedeInsertar;

    @Column(name = "puede_editar", nullable = false)
    private Boolean puedeEditar;

    @Column(name = "puede_eliminar", nullable = false)
    private Boolean puedeEliminar;

    @Column(name = "puede_imprimir", nullable = false)
    private Boolean puedeImprimir;
}
