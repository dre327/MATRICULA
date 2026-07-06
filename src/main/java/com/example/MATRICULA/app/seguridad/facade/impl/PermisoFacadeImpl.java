package com.example.MATRICULA.app.seguridad.facade.impl;

import com.example.MATRICULA.app.seguridad.facade.PermisoFacade;
import com.example.MATRICULA.app.seguridad.service.RolFuncionalidadService;
import com.example.MATRICULA.config.mapper.SeguridadMapper;
import com.example.MATRICULA.dto.permiso.AplicarPermisosRequest;
import com.example.MATRICULA.dto.permiso.PermisoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermisoFacadeImpl implements PermisoFacade {

    private final RolFuncionalidadService service;
    private final SeguridadMapper mapper;

    @Override
    public List<PermisoResponse> listarPorRol(Integer idRol) {
        return mapper.toPermisoResponseList(service.listarPermisosDeRol(idRol));
    }

    @Override
    public void aplicar(AplicarPermisosRequest req) {
        service.aplicarPermisos(req);
    }
}
