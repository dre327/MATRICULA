package com.example.MATRICULA.app.academico.facade;

import com.example.MATRICULA.dto.alumno.AlumnoCreateRequest;
import com.example.MATRICULA.dto.alumno.AlumnoFiltroRequest;
import com.example.MATRICULA.dto.alumno.AlumnoResponse;
import com.example.MATRICULA.dto.alumno.AlumnoUpdateRequest;
import com.example.MATRICULA.dto.comun.PageResponse;

public interface AlumnoFacade {

    PageResponse<AlumnoResponse> buscar(AlumnoFiltroRequest filtro);

    AlumnoResponse obtener(Integer idAlumno);

    AlumnoResponse crear(AlumnoCreateRequest req);

    AlumnoResponse actualizar(Integer idAlumno, AlumnoUpdateRequest req);

    void eliminar(Integer idAlumno);
}
