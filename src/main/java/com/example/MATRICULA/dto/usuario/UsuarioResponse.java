package com.example.MATRICULA.dto.usuario;

import com.example.MATRICULA.dto.rol.RolResponse;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponse {

    private Integer idUsuario;
    private String username;
    private String nombreCompleto;
    private RolResponse rol;
    private Boolean estado;
    private LocalDateTime fecInsert;
}
