package com.example.MATRICULA.dto.tipoDocumento;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoDocumentoResponse {

    private Integer idTipoDoc;
    private String descripcion;
    private Boolean estado;
}
