package com.example.MATRICULA.app.seguridad.service.impl;

import com.example.MATRICULA.app.seguridad.entity.Funcionalidad;
import com.example.MATRICULA.app.seguridad.entity.Rol;
import com.example.MATRICULA.app.seguridad.entity.RolFuncionalidad;
import com.example.MATRICULA.app.seguridad.entity.vista.RolPermisoVista;
import com.example.MATRICULA.app.seguridad.repository.FuncionalidadRepository;
import com.example.MATRICULA.app.seguridad.repository.RolFuncionalidadRepository;
import com.example.MATRICULA.app.seguridad.repository.RolRepository;
import com.example.MATRICULA.app.seguridad.repository.vista.RolPermisoVistaRepository;
import com.example.MATRICULA.app.seguridad.service.RolFuncionalidadService;
import com.example.MATRICULA.common.Constantes;
import com.example.MATRICULA.common.exception.RecursoNoEncontradoException;
import com.example.MATRICULA.common.service.BaseServiceImpl;
import com.example.MATRICULA.dto.permiso.AplicarPermisosRequest;
import com.example.MATRICULA.dto.permiso.PermisoItem;
import com.example.MATRICULA.security.PermisoCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RolFuncionalidadServiceImpl extends BaseServiceImpl implements RolFuncionalidadService {

    private final RolFuncionalidadRepository rolFuncRepo;
    private final RolRepository rolRepo;
    private final FuncionalidadRepository funcRepo;
    private final RolPermisoVistaRepository rolPermisoVistaRepo;
    private final PermisoCache permisoCache;

    @Override
    @Transactional(readOnly = true)
    public List<RolPermisoVista> listarPermisosDeRol(Integer idRol) {
        return rolPermisoVistaRepo.findByIdRolOrderByOrden(idRol);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void aplicarPermisos(AplicarPermisosRequest req) {
        Rol rol = rolRepo.findById(req.getIdRol())
                .orElseThrow(() -> new RecursoNoEncontradoException("Rol", req.getIdRol()));

        // 1. Borrar permisos actuales del rol
        rolFuncRepo.deleteByRolIdRol(rol.getIdRol());
        // Flush explícito para asegurar que el DELETE ocurra antes del INSERT
        // (evita choques con la UK uk_rol_func).
        rolFuncRepo.flush();

        // 2. Insertar los nuevos permisos
        List<RolFuncionalidad> nuevos = req.getPermisos().stream()
                .map(item -> construir(rol, item))
                .toList();
        rolFuncRepo.saveAll(nuevos);

        // 3. Invalidar caché del rol (así el próximo request lee los permisos frescos)
        permisoCache.invalidar(rol.getIdRol());

        // 4. Auditar
        auditar(Constantes.MODULO_PERMISOS, "rol_funcionalidad", Constantes.OP_APLICAR_PERMISOS,
                rol.getIdRol(), null,
                Map.of("idRol", rol.getIdRol(),
                       "nombreRol", rol.getNombre(),
                       "cantidadPermisos", nuevos.size()));
    }

    private RolFuncionalidad construir(Rol rol, PermisoItem item) {
        Funcionalidad func = funcRepo.findById(item.getIdFuncionalidad())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Funcionalidad", item.getIdFuncionalidad()));

        return RolFuncionalidad.builder()
                .rol(rol)
                .funcionalidad(func)
                .puedeVer(item.getPuedeVer())
                .puedeInsertar(item.getPuedeInsertar())
                .puedeEditar(item.getPuedeEditar())
                .puedeEliminar(item.getPuedeEliminar())
                .puedeImprimir(item.getPuedeImprimir())
                .estado(Boolean.TRUE)
                .usuInsert(session.username())
                .build();
    }
}
