package com.example.MATRICULA.app.matricula.facade.impl;

import com.example.MATRICULA.app.matricula.entity.Cuota;
import com.example.MATRICULA.app.matricula.entity.Matricula;
import com.example.MATRICULA.app.matricula.facade.MatriculaFacade;
import com.example.MATRICULA.app.matricula.service.MatriculaService;
import com.example.MATRICULA.config.mapper.MatriculaMapper;
import com.example.MATRICULA.dto.matricula.MatriculaResponse;
import com.example.MATRICULA.dto.matricula.MatricularRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatriculaFacadeImpl implements MatriculaFacade {

    private final MatriculaService service;
    private final MatriculaMapper mapper;

    @Override
    public MatriculaResponse matricular(MatricularRequest req) {
        Matricula m = service.matricular(req);
        List<Cuota> cuotas = service.listarCuotasDeMatricula(m.getIdMatricula());

        MatriculaResponse resp = mapper.toResponse(m);
        resp.setCuotas(mapper.toCuotaResponseList(cuotas));
        return resp;
    }
}
