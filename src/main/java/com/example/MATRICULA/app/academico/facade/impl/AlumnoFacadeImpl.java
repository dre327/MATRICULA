package com.example.MATRICULA.app.academico.facade.impl;

import com.example.MATRICULA.app.academico.entity.Alumno;
import com.example.MATRICULA.app.academico.facade.AlumnoFacade;
import com.example.MATRICULA.app.academico.service.AlumnoService;
import com.example.MATRICULA.config.mapper.AcademicoMapper;
import com.example.MATRICULA.dto.alumno.AlumnoCreateRequest;
import com.example.MATRICULA.dto.alumno.AlumnoFiltroRequest;
import com.example.MATRICULA.dto.alumno.AlumnoResponse;
import com.example.MATRICULA.dto.alumno.AlumnoUpdateRequest;
import com.example.MATRICULA.dto.comun.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlumnoFacadeImpl implements AlumnoFacade {

    private final AlumnoService service;
    private final AcademicoMapper mapper;

    @Override
    public PageResponse<AlumnoResponse> buscar(AlumnoFiltroRequest filtro) {
        Page<Alumno> page = service.buscar(filtro);
        return PageResponse.of(page.map(mapper::toResponse));
    }

    @Override
    public AlumnoResponse obtener(Integer idAlumno) {
        return mapper.toResponse(service.obtener(idAlumno));
    }

    @Override
    public AlumnoResponse crear(AlumnoCreateRequest req) {
        return mapper.toResponse(service.crear(req));
    }

    @Override
    public AlumnoResponse actualizar(Integer idAlumno, AlumnoUpdateRequest req) {
        return mapper.toResponse(service.actualizar(idAlumno, req));
    }

    @Override
    public void eliminar(Integer idAlumno) {
        service.eliminar(idAlumno);
    }
}
