package com.agroconnect.backend.auth;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.agroconnect.backend.dto.JwtAuthResponseDto;
import com.agroconnect.backend.dto.LoginDto;
import com.agroconnect.backend.dto.RegistroDto;
import com.agroconnect.backend.entity.Usuario;
import com.agroconnect.backend.jwt.JwtTokenProvider;
import com.agroconnect.backend.repository.UsuarioRepository;
import com.agroconnect.backend.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", allowCredentials = "false")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDto> login(@Valid @RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuario = usuarioRepository.findByCorreo(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        JwtAuthResponseDto response = new JwtAuthResponseDto(
                token,
                "Bearer",
                usuario.getCorreo(),
                usuario.getNombres(),
                usuario.getApellidos());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/registro")
    public ResponseEntity<String> registro(@Valid @RequestBody RegistroDto registroDto) {
        try {
            String respuesta = authService.registro(registroDto);
            return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al registrar usuario: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}