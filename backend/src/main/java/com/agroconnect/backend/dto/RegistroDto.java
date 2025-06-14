package com.agroconnect.backend.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegistroDto {
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El formato del correo no es válido")
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String contraseña;

    @NotBlank(message = "Los nombres son obligatorios")
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios")
    private String apellidos;

    @NotNull(message = "El tipo de documento es obligatorio")
    private Integer idTipoDocumento;

    @NotBlank(message = "El número de documento es obligatorio")
    private String numeroDocumento;

    @Pattern(regexp = "^\\d{9}$", message = "El teléfono debe tener 9 dígitos")
    private String telefono;

    @NotBlank(message = "El código de ubigeo es obligatorio")
    @Pattern(regexp = "^\\d{6}$", message = "El código de ubigeo debe tener 6 dígitos")
    private String codigoUbigeo;
}