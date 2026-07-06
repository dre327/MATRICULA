package com.example.MATRICULA.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Estado de pago de una cuota.
 * Se persiste como CHAR(1) en cuota.estado_pago.
 */
@Getter
@RequiredArgsConstructor
public enum EstadoPago {

    PENDIENTE('P'),
    CANCELADO('C'),
    ANULADO('A');

    private final Character codigo;

    public static EstadoPago fromCodigo(Character c) {
        if (c == null) return null;
        for (EstadoPago e : values()) {
            if (e.codigo.equals(c)) return e;
        }
        throw new IllegalArgumentException("Estado de pago desconocido: " + c);
    }
}
