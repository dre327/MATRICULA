package com.example.MATRICULA.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración explícita del ObjectMapper.
 *
 * En Spring Boot 4 con spring-boot-starter-webmvc, el ObjectMapper bean no
 * se auto-configura de la misma manera que en el clásico spring-boot-starter-web.
 * Lo declaramos manualmente aquí para que:
 *   - Esté disponible para inyección (usado por AuditoriaServiceImpl).
 *   - Spring MVC lo use como conversor JSON default.
 *   - Sepa serializar java.time (LocalDate, LocalDateTime) en ISO-8601
 *     en vez de timestamps numéricos.
 */
@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}
