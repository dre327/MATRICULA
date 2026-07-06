package com.example.MATRICULA.app.seguridad.service;

import com.example.MATRICULA.app.seguridad.entity.Usuario;
import com.example.MATRICULA.dto.usuario.UsuarioCreateRequest;
import com.example.MATRICULA.dto.usuario.UsuarioUpdateRequest;

import java.util.List;

public interface UsuarioService {

    List<Usuario> listarActivos();

    Usuario obtener(Integer idUsuario);

    Usuario crear(UsuarioCreateRequest req);

    Usuario actualizar(Integer idUsuario, UsuarioUpdateRequest req);

    void cambiarPassword(Integer idUsuario, String passwordActual, String passwordNueva);

    void eliminar(Integer idUsuario);
}
