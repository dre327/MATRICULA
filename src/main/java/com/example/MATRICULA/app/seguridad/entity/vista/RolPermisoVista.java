package com.example.MATRICULA.app.seguridad.entity.vista;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "vw_rol_permiso")
@Getter
@Setter
public class RolPermisoVista {

    @Id
    private Integer id;

    @Column(name = "id_rol")
    private Integer idRol;

    private String rol;

    @Column(name = "id_funcionalidad")
    private Integer idFuncionalidad;

    private String funcionalidad;

    private String url;

    private String icono;

    @Column(name = "id_padre")
    private Integer idPadre;

    private String padre;

    private Integer orden;

    @Column(name = "puede_ver")
    private Boolean puedeVer;

    @Column(name = "puede_insertar")
    private Boolean puedeInsertar;

    @Column(name = "puede_editar")
    private Boolean puedeEditar;

    @Column(name = "puede_eliminar")
    private Boolean puedeEliminar;

    @Column(name = "puede_imprimir")
    private Boolean puedeImprimir;
}
