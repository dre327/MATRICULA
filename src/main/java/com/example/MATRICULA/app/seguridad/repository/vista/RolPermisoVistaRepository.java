package com.example.MATRICULA.app.seguridad.repository.vista;

import com.example.MATRICULA.app.seguridad.entity.vista.RolPermisoVista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolPermisoVistaRepository extends JpaRepository<RolPermisoVista, Integer> {

    List<RolPermisoVista> findByIdRolOrderByOrden(Integer idRol);

    List<RolPermisoVista> findByIdRolAndPuedeVerTrueOrderByOrden(Integer idRol);
}
