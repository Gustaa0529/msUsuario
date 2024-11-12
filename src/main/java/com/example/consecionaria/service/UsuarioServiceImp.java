package com.example.consecionaria.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.consecionaria.dto.SucursalDto;
import com.example.consecionaria.dto.UsuarioDto;
import com.example.consecionaria.entity.Sucursal;
import com.example.consecionaria.entity.Usuario;
import com.example.consecionaria.repository.SucursalRepository;
import com.example.consecionaria.repository.UsuarioRepository;

@Service
public class UsuarioServiceImp implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private SucursalRepository sucursalRepositoy;

    private Usuario convertToEntity(UsuarioDto usuarioDto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDto.getNombre());
        usuario.setCorreo(usuarioDto.getCorreo());
        usuario.setContrasena(usuarioDto.getContrasena());
        usuario.setSucursal(new Sucursal(usuarioDto.getIdSucursal()));
        usuario.setRol(usuarioDto.getRol());
        return usuario;
    }

    private UsuarioDto convertToDto(Usuario usuario) {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setIdUsuario(usuario.getIdUsuario());
        usuarioDto.setNombre(usuario.getNombre());
        usuarioDto.setCorreo(usuario.getCorreo());
        usuarioDto.setContrasena(usuario.getContrasena());
        usuarioDto.setIdSucursal(usuario.getSucursal().getIdSucursal());
        usuarioDto.setRol(usuario.getRol()); 
        return usuarioDto;
    }
    
    private SucursalDto convertToDto(Sucursal sucursal) {
    	SucursalDto sucursalDto = new SucursalDto();
    	sucursalDto.setIdSucursal(sucursal.getIdSucursal()); 
    	sucursalDto.setDireccion(sucursal.getDireccion()); 
    	return sucursalDto; 
    	}

    public UsuarioDto saveUsuario(UsuarioDto usuarioDto) {
    	if (usuarioRepository.findByCorreo(usuarioDto.getCorreo()).isPresent()){ 
    		throw new RuntimeException("Ya existe un usuario con este correo");
    		} 
    	Usuario usuario = convertToEntity(usuarioDto); 
    	Usuario savedUsuario = usuarioRepository.save(usuario); 
    	return convertToDto(savedUsuario);
    }

    public UsuarioDto findByCorreo(String correo) {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return convertToDto(usuario);
    }

	@Override
	public UsuarioDto verificarCredenciales(String correo, String contrasena) throws Exception {
		 Usuario usuario = usuarioRepository.findByCorreo(correo)
	                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
	        if (new BCryptPasswordEncoder().matches(contrasena, usuario.getContrasena())) {
	            return convertToDto(usuario);
	        } else {
	            throw new Exception("Credenciales inválidas");
	        }
	}
	
	public Page<UsuarioDto> listarUsuariosPaginados(Integer size, String sort, Integer numPage) throws Exception {
	    if (size <= 0) {
	        throw new IllegalArgumentException("El tamaño de la página debe ser mayor que cero.");
	    }
	    if (numPage < 0) {
	        throw new IllegalArgumentException("El número de página no puede ser negativo.");
	    }
	    if (sort == null || sort.trim().isEmpty()) {
	        sort = "idUsuario";
	    }

	    Pageable paginacion = PageRequest.of(numPage, size, Sort.by(sort));
	    Page<Usuario> pageResult = usuarioRepository.findAll(paginacion);

	    return pageResult.map(this::convertToDto);
	}
	
	public List<SucursalDto> listarSucursales() throws Exception{ 
		List<Sucursal> sucursales = sucursalRepositoy.findAll(); 
		return sucursales.stream().map(this::convertToDto).collect(Collectors.toList()); 
	}

}
