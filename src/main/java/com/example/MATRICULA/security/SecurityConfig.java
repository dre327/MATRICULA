package com.example.MATRICULA.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

/**
 * Configuración de Spring Security.
 * - Sesión HTTP + cookie JSESSIONID (no JWT).
 * - CSRF activo con token en cookie XSRF-TOKEN (leible por JS) y header X-XSRF-TOKEN.
 * - /api/auth/login y /api/auth/logout públicos; el resto requiere autenticación.
 * - Sin login form: el frontend usa el endpoint REST /api/auth/login.
 * - Sin redirects: cuando falta autenticación devuelve 401; cuando falta permiso, 403.
 */
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new org.springframework.security.authentication.ProviderManager(authProvider());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                // Handler clásico: el token se sirve tal cual en la cookie XSRF-TOKEN
                // (sin XOR encoding). El frontend puede leerla y reenviarla directo
                // en el header X-XSRF-TOKEN.
                .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                // El endpoint de login recibe credenciales, no puede exigir CSRF previo
                .ignoringRequestMatchers("/api/auth/login")
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1)   // Un solo login concurrente por usuario
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/api/auth/login",
                        "/api/auth/logout",
                        // Recursos estáticos del frontend
                        "/", "/index.html", "/login.html",
                        "/css/**", "/js/**", "/img/**",
                        "/favicon.ico"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex
                // Si no está autenticado → 401 (no redirect a /login)
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            )
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())
            .logout(logout -> logout
                .logoutUrl("/api/auth/logout")
                .logoutSuccessHandler((req, res, auth) -> res.setStatus(HttpStatus.NO_CONTENT.value()))
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "XSRF-TOKEN")
            )
            // Fuerza el envío de la cookie XSRF-TOKEN en cada respuesta.
            // Sin este filtro, Spring Security 6 la genera sólo perezosamente
            // → el frontend no la recibe → los POST/PUT/DELETE devuelven 403.
            .addFilterAfter(new CsrfCookieFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
