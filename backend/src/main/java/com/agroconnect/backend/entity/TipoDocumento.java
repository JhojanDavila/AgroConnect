package com.agroconnect.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TipoDocumento")
public class TipoDocumento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdTipoDocumento")
    private Integer idTipoDocumento;

    @Column(name = "NombreTipo", nullable = false, unique = true)
    private String nombreTipo;

    @Column(name = "Estado", nullable = false)
    private Boolean estado = true;
}
