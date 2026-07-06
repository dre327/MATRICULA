package com.example.MATRICULA.app.academico.service;

import com.example.MATRICULA.app.academico.entity.Alumno;
import com.example.MATRICULA.dto.alumno.AlumnoCreateRequest;
import com.example.MATRICULA.dto.alumno.AlumnoFiltroRequest;
import com.example.MATRICULA.dto.alumno.AlumnoUpdateRequest;
import org.springframework.data.domain.Page;

public interface AlumnoService {

    Page<Alumno> buscar(AlumnoFiltroRequest filtro);

    Alumno obtener(Integer idAlumno);

    Alumno crear(AlumnoCreateRequest req);

    Alumno actualizar(Integer idAlumno, AlumnoUpdateRequest req);

    void eliminar(Integer idAlumno);
}
