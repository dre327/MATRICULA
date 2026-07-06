package com.example.MATRICULA.security;

import com.example.MATRICULA.app.seguridad.entity.Usuario;
import com.example.MATRICULA.app.seguridad.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Al arrancar la app, revisa todos los usuarios y hashea con BCrypt cualquier
 * password que aún esté en texto plano (no empiece con "$2").
 *
 * Es idempotente: en corridas posteriores no hace nada.
 * Cubre el caso del admin/admin123 seed del script.sql sin pedirte
 * precomputar el hash con un salt específico.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class PasswordMigrationRunner implements ApplicationRunner {

    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        List<Usuario> pendientes = usuarioRepo.findAll().stream()
                .filter(u -> u.getPasswordHash() != null && !u.getPasswordHash().startsWith("$2"))
                .toList();

        if (pendientes.isEmpty()) return;

        log.info("Migrando {} password(s) de texto plano a BCrypt...", pendientes.size());
        for (Usuario u : pendientes) {
            u.setPasswordHash(passwordEncoder.encode(u.getPasswordHash()));
            u.setUsuUpdate("SYSTEM");
            usuarioRepo.save(u);
            log.info("Password migrada para usuario: {}", u.getUsername());
        }
    }
}
