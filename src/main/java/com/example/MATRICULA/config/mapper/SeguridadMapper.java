package com.example.MATRICULA.config.mapper;

import com.example.MATRICULA.app.seguridad.entity.Funcionalidad;
import com.example.MATRICULA.app.seguridad.entity.Rol;
import com.example.MATRICULA.app.seguridad.entity.Usuario;
import com.example.MATRICULA.app.seguridad.entity.vista.RolPermisoVista;
import com.example.MATRICULA.dto.funcionalidad.FuncionalidadResponse;
import com.example.MATRICULA.dto.permiso.PermisoResponse;
import com.example.MATRICULA.dto.rol.RolRequest;
import com.example.MATRICULA.dto.rol.RolResponse;
import com.example.MATRICULA.dto.usuario.UsuarioCreateRequest;
import com.example.MATRICULA.dto.usuario.UsuarioResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper del dominio Seguridad: convierte entre entidades JPA y DTOs.
 * MapStruct genera la implementación en tiempo de compilación.
 * Se inyecta en facades con: private final SeguridadMapper mapper;
 */
@Mapper(componentModel = "spring")
public interface SeguridadMapper {

    // ── Rol ────────────────────────────────────────────────────────────

    RolResponse toResponse(Rol rol);

    List<RolResponse> toRolResponseList(List<Rol> roles);

    @Mapping(target = "idRol",    ignore = true)
    @Mapping(target = "estado",   ignore = true)
    Rol toEntity(RolRequest req);

    // ── Usuario ────────────────────────────────────────────────────────

    @Mapping(target = "rol", source = "rol")
    UsuarioResponse toResponse(Usuario usuario);

    List<UsuarioResponse> toUsuarioResponseList(List<Usuario> usuarios);

    /**
     * Convierte el request en Usuario. El passwordHash y el rol se setean
     * en el service (el request trae la password en texto plano + idRol),
     * por eso los ignoramos aquí.
     */
    @Mapping(target = "idUsuario",    ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "rol",          ignore = true)
    @Mapping(target = "estado",       ignore = true)
    @Mapping(target = "usuInsert",    ignore = true)
    @Mapping(target = "fecInsert",    ignore = true)
    @Mapping(target = "usuUpdate",    ignore = true)
    @Mapping(target = "fecUpdate",    ignore = true)
    Usuario toEntity(UsuarioCreateRequest req);

    // ── Funcionalidad ──────────────────────────────────────────────────

    @Mapping(target = "idPadre", source = "padre.idFuncionalidad")
    FuncionalidadResponse toResponse(Funcionalidad funcionalidad);

    List<FuncionalidadResponse> toFuncionalidadResponseList(List<Funcionalidad> funcionalidades);

    // ── Permiso (desde vw_rol_permiso) ─────────────────────────────────

    PermisoResponse toResponse(RolPermisoVista vista);

    List<PermisoResponse> toPermisoResponseList(List<RolPermisoVista> vistas);
}
