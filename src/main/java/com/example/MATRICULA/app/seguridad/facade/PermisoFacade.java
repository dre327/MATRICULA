package com.example.MATRICULA.app.seguridad.facade;

import com.example.MATRICULA.dto.permiso.AplicarPermisosRequest;
import com.example.MATRICULA.dto.permiso.PermisoResponse;

import java.util.List;

public interface PermisoFacade {

    List<PermisoResponse> listarPorRol(Integer idRol);

    void aplicar(AplicarPermisosRequest req);
}
