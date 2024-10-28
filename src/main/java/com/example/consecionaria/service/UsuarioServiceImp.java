package com.example.consecionaria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.consecionaria.dto.UsuarioDto;
import com.example.consecionaria.entity.Rol;
import com.example.consecionaria.entity.Sucursal;
import com.example.consecionaria.entity.Usuario;
import com.example.consecionaria.repository.UsuarioRepository;

@Service
public class UsuarioServiceImp implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario convertToEntity(UsuarioDto usuarioDto) {
        Usuario usuario = new Usuario();
        // No establecer idUsuario aquí, debe ser generado automáticamente
        usuario.setNombre(usuarioDto.getNombre());
        usuario.setCorreo(usuarioDto.getCorreo());
        usuario.setContrasena(usuarioDto.getContrasena());
        usuario.setSucursal(new Sucursal(usuarioDto.getIdSucursal()));
        usuario.setRol(new Rol(usuarioDto.getIdRol()));
        return usuario;
    }

    private UsuarioDto convertToDto(Usuario usuario) {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setIdUsuario(usuario.getIdUsuario());
        usuarioDto.setNombre(usuario.getNombre());
        usuarioDto.setCorreo(usuario.getCorreo());
        usuarioDto.setContrasena(usuario.getContrasena());
        usuarioDto.setIdSucursal(usuario.getSucursal().getIdSucursal());
        usuarioDto.setIdRol(usuario.getRol().getIdRol());
        return usuarioDto;
    }

    public UsuarioDto saveUsuario(UsuarioDto usuarioDto) {
        Usuario usuario = convertToEntity(usuarioDto);
        Usuario savedUsuario = usuarioRepository.save(usuario);
        return convertToDto(savedUsuario);
    }

    public UsuarioDto findByCorreo(String correo) {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return convertToDto(usuario);
    }
}
