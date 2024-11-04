package com.example.consecionaria.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.consecionaria.dto.UsuarioDto;
import com.example.consecionaria.service.UsuarioService;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
   

    @PostMapping("/registro")
    public ResponseEntity<UsuarioDto> registro(@RequestBody UsuarioDto usuarioDto) throws Exception {
        UsuarioDto usuarioGuardado = usuarioService.saveUsuario(usuarioDto);
        return new ResponseEntity<>(usuarioGuardado, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioDto> login(@RequestBody UsuarioDto usuarioDto) {
        try {
            UsuarioDto usuarioVerificado = usuarioService.verificarCredenciales(usuarioDto.getCorreo(), usuarioDto.getContrasena());
            return new ResponseEntity<>(usuarioVerificado, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }
    
    /*@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpSession session) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getCorreo(),
                        loginRequest.getContrasena()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UsuarioDto usuarioDto = usuarioService.findByCorreo(loginRequest.getCorreo());


        return 
    }*/

    @GetMapping("/perfil")
    public ResponseEntity<UsuarioDto> obtenerPerfil(@SessionAttribute("user") UsuarioDto usuarioDto) {
        return new ResponseEntity<>(usuarioDto, HttpStatus.OK);
    }
}
