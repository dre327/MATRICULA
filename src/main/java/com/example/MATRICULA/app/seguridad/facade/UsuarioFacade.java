package com.example.MATRICULA.app.seguridad.facade;

import com.example.MATRICULA.dto.usuario.CambiarPasswordRequest;
import com.example.MATRICULA.dto.usuario.UsuarioCreateRequest;
import com.example.MATRICULA.dto.usuario.UsuarioResponse;
import com.example.MATRICULA.dto.usuario.UsuarioUpdateRequest;

import java.util.List;

public interface UsuarioFacade {

    List<UsuarioResponse> listar();

    UsuarioResponse obtener(Integer idUsuario);

    UsuarioResponse crear(UsuarioCreateRequest req);

    UsuarioResponse actualizar(Integer idUsuario, UsuarioUpdateRequest req);

    void cambiarPassword(Integer idUsuario, CambiarPasswordRequest req);

    void eliminar(Integer idUsuario);
}
