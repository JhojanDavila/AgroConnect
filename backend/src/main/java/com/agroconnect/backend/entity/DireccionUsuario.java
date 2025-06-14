package com.agroconnect.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DireccionUsuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DireccionUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdDireccion")
    private Integer idDireccion;

    @ManyToOne
    @JoinColumn(name = "IdUsuario", nullable = false)
    private Usuario usuario;

    @Column(name = "DireccionCompleta", nullable = false, length = 255)
    private String direccionCompleta;

    @Column(name = "Referencia", length = 255)
    private String referencia;

    @ManyToOne
    @JoinColumn(name = "CodigoUbigeo", nullable = false)
    private Ubigeo ubigeo;
}
