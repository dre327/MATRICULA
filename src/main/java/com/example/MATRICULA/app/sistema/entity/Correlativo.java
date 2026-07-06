package com.example.MATRICULA.app.sistema.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "correlativo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Correlativo {

    @Id
    @Column(length = 50)
    private String clave;

    @Column(name = "ultimo_valor", nullable = false)
    private Integer ultimoValor;

    @Column(length = 150)
    private String descripcion;
}
