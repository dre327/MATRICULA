package com.example.MATRICULA.app.seguridad.facade.impl;

import com.example.MATRICULA.app.seguridad.facade.UsuarioFacade;
import com.example.MATRICULA.app.seguridad.service.UsuarioService;
import com.example.MATRICULA.config.mapper.SeguridadMapper;
import com.example.MATRICULA.dto.usuario.CambiarPasswordRequest;
import com.example.MATRICULA.dto.usuario.UsuarioCreateRequest;
import com.example.MATRICULA.dto.usuario.UsuarioResponse;
import com.example.MATRICULA.dto.usuario.UsuarioUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioFacadeImpl implements UsuarioFacade {

    private final UsuarioService service;
    private final SeguridadMapper mapper;

    @Override
    public List<UsuarioResponse> listar() {
        return mapper.toUsuarioResponseList(service.listarActivos());
    }

    @Override
    public UsuarioResponse obtener(Integer idUsuario) {
        return mapper.toResponse(service.obtener(idUsuario));
    }

    @Override
    public UsuarioResponse crear(UsuarioCreateRequest req) {
        return mapper.toResponse(service.crear(req));
    }

    @Override
    public UsuarioResponse actualizar(Integer idUsuario, UsuarioUpdateRequest req) {
        return mapper.toResponse(service.actualizar(idUsuario, req));
    }

    @Override
    public void cambiarPassword(Integer idUsuario, CambiarPasswordRequest req) {
        service.cambiarPassword(idUsuario, req.getPasswordActual(), req.getPasswordNueva());
    }

    @Override
    public void eliminar(Integer idUsuario) {
        service.eliminar(idUsuario);
    }
}
