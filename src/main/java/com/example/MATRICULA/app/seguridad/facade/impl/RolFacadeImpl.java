package com.example.MATRICULA.app.seguridad.facade.impl;

import com.example.MATRICULA.app.seguridad.facade.RolFacade;
import com.example.MATRICULA.app.seguridad.service.RolService;
import com.example.MATRICULA.config.mapper.SeguridadMapper;
import com.example.MATRICULA.dto.rol.RolRequest;
import com.example.MATRICULA.dto.rol.RolResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RolFacadeImpl implements RolFacade {

    private final RolService service;
    private final SeguridadMapper mapper;

    @Override
    public List<RolResponse> listar() {
        return mapper.toRolResponseList(service.listarActivos());
    }

    @Override
    public RolResponse obtener(Integer idRol) {
        return mapper.toResponse(service.obtener(idRol));
    }

    @Override
    public RolResponse crear(RolRequest req) {
        return mapper.toResponse(service.crear(req.getNombre(), req.getDescripcion()));
    }

    @Override
    public RolResponse actualizar(Integer idRol, RolRequest req) {
        return mapper.toResponse(service.actualizar(idRol, req.getNombre(), req.getDescripcion()));
    }

    @Override
    public void eliminar(Integer idRol) {
        service.eliminar(idRol);
    }
}
