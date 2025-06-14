package com.agroconnect.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdUsuario")
    private Integer idUsuario;

    @Column(name = "Correo", nullable = false, unique = true, length = 100)
    private String correo;

    @Column(name = "ContraseñaHash", nullable = false)
    private byte[] contraseñaHash;

    @Column(name = "Salt", nullable = false)
    private byte[] salt;

    @Column(name = "Nombres", nullable = false, length = 100)
    private String nombres;

    @Column(name = "Apellidos", nullable = false, length = 100)
    private String apellidos;

    @ManyToOne
    @JoinColumn(name = "IdTipoDocumento", nullable = false)
    private TipoDocumento tipoDocumento;

    @Column(name = "NumeroDocumento", nullable = false, unique = true, length = 20)
    private String numeroDocumento;

    @Column(name = "Telefono", length = 15)
    private String telefono;

    @Column(name = "FotoPerfil", length = 255)
    private String fotoPerfil;

    @Column(name = "Estado", nullable = false)
    private Boolean estado = true;

    @ManyToOne
    @JoinColumn(name = "CodigoUbigeo", nullable = false)
    private Ubigeo ubigeo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "UsuarioRol", joinColumns = @JoinColumn(name = "IdUsuario"), inverseJoinColumns = @JoinColumn(name = "IdRol"))
    private Set<Rol> roles = new HashSet<>();
}
