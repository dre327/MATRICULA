package com.example.MATRICULA.app.academico.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tipo_documento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoDocumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_doc")
    private Integer idTipoDoc;

    @Column(nullable = false, length = 50)
    private String descripcion;

    @Column(nullable = false)
    private Boolean estado;
}
