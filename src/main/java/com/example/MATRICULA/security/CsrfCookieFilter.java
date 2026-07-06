package com.example.MATRICULA.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Fuerza que la cookie XSRF-TOKEN se envíe al frontend en cada respuesta.
 *
 * Sin este filtro, Spring Security 6 sólo genera la cookie CSRF cuando algún
 * código accede explícitamente al token (perezosamente). Como nuestros controllers
 * no lo hacen, la cookie nunca se seteaba en el navegador, y las requests
 * POST/PUT/DELETE del frontend no podían enviar el header X-XSRF-TOKEN,
 * causando 403 Forbidden.
 *
 * Este filtro llama getToken() en cada request, lo que dispara internamente el
 * Set-Cookie en la respuesta.
 */
public class CsrfCookieFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrfToken != null) {
            csrfToken.getToken();   // dispara Set-Cookie XSRF-TOKEN
        }
        filterChain.doFilter(request, response);
    }
}
