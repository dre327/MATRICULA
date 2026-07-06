package com.example.MATRICULA.app.seguridad.repository;

import com.example.MATRICULA.app.seguridad.entity.RolFuncionalidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolFuncionalidadRepository extends JpaRepository<RolFuncionalidad, Integer> {

    List<RolFuncionalidad> findByRolIdRol(Integer idRol);

    /**
     * Borra todos los permisos de un rol. Debe llamarse dentro de una
     * transacción — el rollback la revierte si algo falla luego.
     */
    void deleteByRolIdRol(Integer idRol);
}
