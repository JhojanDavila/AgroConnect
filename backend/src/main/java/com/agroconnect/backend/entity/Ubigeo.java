package com.agroconnect.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Ubigeo")
public class Ubigeo {
    @Id
    @Column(name = "CodigoUbigeo", length = 6)
    private String codigoUbigeo;

    @Column(name = "Departamento", nullable = false)
    private String departamento;

    @Column(name = "Provincia", nullable = false)
    private String provincia;

    @Column(name = "Distrito", nullable = false)
    private String distrito;
}
