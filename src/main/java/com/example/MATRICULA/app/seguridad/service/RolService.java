package com.example.MATRICULA.app.seguridad.service;

import com.example.MATRICULA.app.seguridad.entity.Rol;

import java.util.List;

public interface RolService {

    List<Rol> listarActivos();

    Rol obtener(Integer idRol);

    Rol crear(String nombre, String descripcion);

    Rol actualizar(Integer idRol, String nombre, String descripcion);

    void eliminar(Integer idRol);
}
