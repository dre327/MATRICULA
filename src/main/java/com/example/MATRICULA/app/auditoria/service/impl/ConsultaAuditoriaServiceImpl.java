package com.example.MATRICULA.app.auditoria.service.impl;

import com.example.MATRICULA.app.auditoria.entity.vista.AuditoriaVista;
import com.example.MATRICULA.app.auditoria.repository.vista.AuditoriaVistaRepository;
import com.example.MATRICULA.app.auditoria.service.ConsultaAuditoriaService;
import com.example.MATRICULA.dto.auditoria.AuditoriaFiltroRequest;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultaAuditoriaServiceImpl implements ConsultaAuditoriaService {

    private final AuditoriaVistaRepository repo;

    @Override
    @Transactional(readOnly = true)
    public Page<AuditoriaVista> consultar(AuditoriaFiltroRequest filtro) {
        return repo.findAll(
                (root, query, cb) -> {
                    List<Predicate> preds = new ArrayList<>();

                    if (hasText(filtro.getUsuario()))
                        preds.add(cb.equal(root.get("usuario"), filtro.getUsuario()));
                    if (hasText(filtro.getModulo()))
                        preds.add(cb.equal(root.get("modulo"), filtro.getModulo()));
                    if (hasText(filtro.getTabla()))
                        preds.add(cb.equal(root.get("tablaAfectada"), filtro.getTabla()));
                    if (hasText(filtro.getOperacion()))
                        preds.add(cb.equal(root.get("operacion"), filtro.getOperacion()));
                    if (filtro.getFechaInicio() != null)
                        preds.add(cb.greaterThanOrEqualTo(root.get("fechaHora"), filtro.getFechaInicio()));
                    if (filtro.getFechaFin() != null)
                        preds.add(cb.lessThanOrEqualTo(root.get("fechaHora"), filtro.getFechaFin()));

                    return cb.and(preds.toArray(new Predicate[0]));
                },
                PageRequest.of(filtro.getPagina(), filtro.getTamano(),
                        Sort.by(Sort.Direction.DESC, "fechaHora"))
        );
    }

    private static boolean hasText(String s) {
        return s != null && !s.trim().isEmpty();
    }
}
