package com.example.MATRICULA.app.seguridad.auth.service.impl;

import com.example.MATRICULA.app.seguridad.auth.service.AuthService;
import com.example.MATRICULA.app.seguridad.entity.Usuario;
import com.example.MATRICULA.app.seguridad.entity.vista.RolPermisoVista;
import com.example.MATRICULA.app.seguridad.repository.UsuarioRepository;
import com.example.MATRICULA.app.seguridad.repository.vista.RolPermisoVistaRepository;
import com.example.MATRICULA.common.exception.RecursoNoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepo;
    private final RolPermisoVistaRepository rolPermisoVistaRepo;

    @Override
    public Authentication autenticar(String username, String password) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario cargarUsuario(Integer idUsuario) {
        return usuarioRepo.findById(idUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario", idUsuario));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RolPermisoVista> cargarPermisos(Integer idRol) {
        return rolPermisoVistaRepo.findByIdRolAndPuedeVerTrueOrderByOrden(idRol);
    }
}
