package com.example.MATRICULA.app.seguridad.facade;

import com.example.MATRICULA.dto.rol.RolRequest;
import com.example.MATRICULA.dto.rol.RolResponse;

import java.util.List;

public interface RolFacade {

    List<RolResponse> listar();

    RolResponse obtener(Integer idRol);

    RolResponse crear(RolRequest req);

    RolResponse actualizar(Integer idRol, RolRequest req);

    void eliminar(Integer idRol);
}
