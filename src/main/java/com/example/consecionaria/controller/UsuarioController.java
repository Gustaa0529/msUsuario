package com.example.consecionaria.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.consecionaria.dto.PaginadoDto;
import com.example.consecionaria.dto.SucursalDto;
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
    
    @GetMapping("/correo/{correo}")
    public ResponseEntity<UsuarioDto> obtenerPorCorreo(@PathVariable String correo) {
        try {
            UsuarioDto usuarioDto = usuarioService.findByCorreo(correo);
            return new ResponseEntity<>(usuarioDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
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
    
    @GetMapping(value = "/listar/paginado", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PaginadoDto<UsuarioDto>> listarConPaginado(
			@RequestParam Integer size,
			@RequestParam String sort,
			@RequestParam Integer numPage
			) throws Exception {
		Page<UsuarioDto> pageResult = usuarioService.listarUsuariosPaginados(size, sort, numPage);
		PaginadoDto<UsuarioDto> response = new PaginadoDto<>(
				pageResult.getContent(),
				pageResult.getTotalElements(),
				pageResult.getTotalPages(),
				pageResult.getSize(),
				pageResult.getNumber()
				);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
    
    @GetMapping("/sucursales")
    public ResponseEntity<List<SucursalDto>> listarSucursales() throws Exception {
        List<SucursalDto> sucursales = usuarioService.listarSucursales();
        return new ResponseEntity<>(sucursales, HttpStatus.OK);
    }
}
