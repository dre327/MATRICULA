package com.example.MATRICULA.app.seguridad.repository;

import com.example.MATRICULA.app.seguridad.entity.Funcionalidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuncionalidadRepository extends JpaRepository<Funcionalidad, Integer> {

    List<Funcionalidad> findByEstadoTrueOrderByOrden();

    List<Funcionalidad> findByPadreIsNullAndEstadoTrueOrderByOrden();

    List<Funcionalidad> findByPadreIdFuncionalidadAndEstadoTrueOrderByOrden(Integer idPadre);
}
