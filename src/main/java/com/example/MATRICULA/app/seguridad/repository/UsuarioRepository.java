package com.example.MATRICULA.app.seguridad.repository;

import com.example.MATRICULA.app.seguridad.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByUsername(String username);

    Optional<Usuario> findByUsernameAndEstadoTrue(String username);

    boolean existsByUsername(String username);

    List<Usuario> findByEstadoTrue();
}
