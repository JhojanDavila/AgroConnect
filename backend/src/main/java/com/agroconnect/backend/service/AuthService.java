package com.agroconnect.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agroconnect.backend.dto.LoginDto;
import com.agroconnect.backend.dto.RegistroDto;
import com.agroconnect.backend.entity.Rol;
import com.agroconnect.backend.entity.TipoDocumento;
import com.agroconnect.backend.entity.Ubigeo;
import com.agroconnect.backend.entity.Usuario;
import com.agroconnect.backend.repository.RolRepository;
import com.agroconnect.backend.repository.TipoDocumentoRepository;
import com.agroconnect.backend.repository.UbigeoRepository;
import com.agroconnect.backend.repository.UsuarioRepository;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;

    @Autowired
    private UbigeoRepository ubigeoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getCorreo(),
                        loginDto.getContraseña()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "Usuario autenticado exitosamente";
    }

    @Transactional
    public String registro(RegistroDto registroDto) {
        // Verificar si el correo ya existe
        if (usuarioRepository.existsByCorreo(registroDto.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        // Verificar si el número de documento ya existe
        if (usuarioRepository.existsByNumeroDocumento(registroDto.getNumeroDocumento())) {
            throw new RuntimeException("El número de documento ya está registrado");
        }

        // Obtener el tipo de documento
        TipoDocumento tipoDocumento = tipoDocumentoRepository.findById(registroDto.getIdTipoDocumento())
                .orElseThrow(() -> new RuntimeException("Tipo de documento no encontrado"));

        // Obtener el ubigeo
        Ubigeo ubigeo = ubigeoRepository.findById(registroDto.getCodigoUbigeo())
                .orElseThrow(() -> new RuntimeException("Ubigeo no encontrado"));

        // Crear el usuario
        Usuario usuario = new Usuario();
        usuario.setCorreo(registroDto.getCorreo());
        usuario.setContraseñaHash(passwordEncoder.encode(registroDto.getContraseña()).getBytes());
        usuario.setSalt(new byte[0]); // No necesitamos salt con BCrypt
        usuario.setNombres(registroDto.getNombres());
        usuario.setApellidos(registroDto.getApellidos());
        usuario.setTipoDocumento(tipoDocumento);
        usuario.setNumeroDocumento(registroDto.getNumeroDocumento());
        usuario.setTelefono(registroDto.getTelefono());
        usuario.setUbigeo(ubigeo);
        usuario.setEstado(true);

        // Asignar rol por defecto (COMPRADOR)
        Set<Rol> roles = new HashSet<>();
        Rol rolComprador = rolRepository.findByNombreRol("COMPRADOR")
                .orElseThrow(() -> new RuntimeException("Rol COMPRADOR no encontrado"));
        roles.add(rolComprador);
        usuario.setRoles(roles);

        usuarioRepository.save(usuario);

        return "Usuario registrado exitosamente";
    }
}