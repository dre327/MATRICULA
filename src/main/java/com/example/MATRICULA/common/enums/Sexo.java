package com.example.MATRICULA.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Categoría de sexo para alumnos.
 * Se persiste como CHAR(1) en alumno.sexo.
 */
@Getter
@RequiredArgsConstructor
public enum Sexo {

    MASCULINO('M'),
    FEMENINO('F');

    private final Character codigo;

    public static Sexo fromCodigo(Character c) {
        if (c == null) return null;
        for (Sexo s : values()) {
            if (s.codigo.equals(c)) return s;
        }
        throw new IllegalArgumentException("Sexo desconocido: " + c);
    }
}
