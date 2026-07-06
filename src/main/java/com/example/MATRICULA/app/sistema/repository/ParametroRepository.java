package com.example.MATRICULA.app.sistema.repository;

import com.example.MATRICULA.app.sistema.entity.Parametro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParametroRepository extends JpaRepository<Parametro, Integer> {

    Optional<Parametro> findByClave(String clave);
}
