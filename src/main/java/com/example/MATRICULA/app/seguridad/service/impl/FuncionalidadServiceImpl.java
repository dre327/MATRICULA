package com.example.MATRICULA.app.seguridad.service.impl;

import com.example.MATRICULA.app.seguridad.entity.Funcionalidad;
import com.example.MATRICULA.app.seguridad.repository.FuncionalidadRepository;
import com.example.MATRICULA.app.seguridad.service.FuncionalidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FuncionalidadServiceImpl implements FuncionalidadService {

    private final FuncionalidadRepository repo;

    @Override
    @Transactional(readOnly = true)
    public List<Funcionalidad> listarActivos() {
        return repo.findByEstadoTrueOrderByOrden();
    }
}
