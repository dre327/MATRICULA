package com.example.MATRICULA.security;

import com.example.MATRICULA.app.seguridad.entity.Usuario;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Envuelve nuestra entidad Usuario para que Spring Security la reconozca.
 * Se expone el idUsuario y el idRol para consultarlos desde SessionContext
 * sin tener que ir a BD.
 */
@Getter
public class CustomUserDetails implements UserDetails {

    private final Integer idUsuario;
    private final Integer idRol;
    private final String nombreRol;
    private final String username;
    private final String password;
    private final String nombreCompleto;
    private final boolean activo;

    public CustomUserDetails(Usuario usuario) {
        this.idUsuario      = usuario.getIdUsuario();
        this.idRol          = usuario.getRol().getIdRol();
        this.nombreRol      = usuario.getRol().getNombre();
        this.username       = usuario.getUsername();
        this.password       = usuario.getPasswordHash();
        this.nombreCompleto = usuario.getNombreCompleto();
        this.activo         = Boolean.TRUE.equals(usuario.getEstado());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + nombreRol));
    }

    @Override
    public boolean isAccountNonExpired()     { return true; }

    @Override
    public boolean isAccountNonLocked()      { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled()               { return activo; }
}
