package com.example.MATRICULA.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
public abstract class AuditableEntity {

    @Column(nullable = false)
    private Boolean estado;

    @Column(name = "usu_insert", length = 50)
    private String usuInsert;

    @Column(name = "fec_insert")
    private LocalDateTime fecInsert;

    @Column(name = "usu_update", length = 50)
    private String usuUpdate;

    @Column(name = "fec_update")
    private LocalDateTime fecUpdate;

    @PrePersist
    protected void onCreate() {
        if (estado == null) estado = Boolean.TRUE;
        fecInsert = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        fecUpdate = LocalDateTime.now();
    }
}
