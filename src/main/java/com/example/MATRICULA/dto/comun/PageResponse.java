package com.example.MATRICULA.dto.comun;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Response envolvente para consultas paginadas.
 * Se construye con {@link #of(Page)} a partir de un Page<T> de Spring Data.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponse<T> {

    private List<T> contenido;
    private Integer pagina;
    private Integer tamano;
    private Long totalRegistros;
    private Integer totalPaginas;

    public static <T> PageResponse<T> of(Page<T> page) {
        return PageResponse.<T>builder()
                .contenido(page.getContent())
                .pagina(page.getNumber())
                .tamano(page.getSize())
                .totalRegistros(page.getTotalElements())
                .totalPaginas(page.getTotalPages())
                .build();
    }
}
