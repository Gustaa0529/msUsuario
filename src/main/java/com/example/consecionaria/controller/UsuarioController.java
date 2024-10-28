package com.example.consecionaria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.consecionaria.dto.UsuarioDto;
import com.example.consecionaria.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

import com.example.consecionaria.config.JwtTokenProvider;
import com.example.consecionaria.dto.JwtResponse;
import com.example.consecionaria.dto.LoginRequest;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;



@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/registro")
    public ResponseEntity<UsuarioDto> registro(@RequestBody UsuarioDto usuarioDto) throws Exception {
        UsuarioDto usuarioGuardado = usuarioService.saveUsuario(usuarioDto);
        return new ResponseEntity<>(usuarioGuardado, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpSession session) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getCorreo(),
                        loginRequest.getContrasena()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UsuarioDto usuarioDto = usuarioService.findByCorreo(loginRequest.getCorreo());
        String jwt = jwtTokenProvider.createToken(authentication, usuarioDto);

        // Guardar el usuario en la sesión de Redis
        session.setAttribute("user", usuarioDto);

        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @GetMapping("/perfil")
    public ResponseEntity<UsuarioDto> obtenerPerfil(@SessionAttribute("user") UsuarioDto usuarioDto) {
        return new ResponseEntity<>(usuarioDto, HttpStatus.OK);
    }
}
