package com.example.MATRICULA.app.seguridad.repository;

import com.example.MATRICULA.app.seguridad.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Integer> {

    List<Rol> findByEstadoTrue();

    Optional<Rol> findByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCase(String nombre);
}
