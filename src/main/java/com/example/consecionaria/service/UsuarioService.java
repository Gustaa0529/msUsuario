package com.example.consecionaria.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.consecionaria.dto.SucursalDto;
import com.example.consecionaria.dto.UsuarioDto;

public interface UsuarioService {

	public UsuarioDto saveUsuario(UsuarioDto usuarioDto) throws Exception;
	UsuarioDto verificarCredenciales(String correo, String contrasena) throws Exception;
	public UsuarioDto findByCorreo(String correo) throws Exception;
	public Page<UsuarioDto> listarUsuariosPaginados(Integer size, String sort, Integer numPage) throws Exception ;
	public List<SucursalDto> listarSucursales() throws Exception;
}
