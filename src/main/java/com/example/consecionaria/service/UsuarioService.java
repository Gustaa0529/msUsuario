package com.example.consecionaria.service;

import com.example.consecionaria.dto.UsuarioDto;

public interface UsuarioService {

	public UsuarioDto saveUsuario(UsuarioDto usuarioDto) throws Exception;
	UsuarioDto verificarCredenciales(String correo, String contrasena) throws Exception;
	public UsuarioDto findByCorreo(String correo) throws Exception;
}
