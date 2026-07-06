package com.example.MATRICULA.app.seguridad.service;

import com.example.MATRICULA.app.seguridad.entity.vista.RolPermisoVista;
import com.example.MATRICULA.dto.permiso.AplicarPermisosRequest;

import java.util.List;

public interface RolFuncionalidadService {

    List<RolPermisoVista> listarPermisosDeRol(Integer idRol);

    /**
     * Reemplaza todos los permisos del rol con los que vienen en el request.
     * Borra los actuales + inserta los nuevos + invalida caché + audita — todo en una transacción.
     */
    void aplicarPermisos(AplicarPermisosRequest req);
}
