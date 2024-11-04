package com.example.consecionaria.dto;

import java.io.Serializable;

import com.example.consecionaria.enums.Rol;

import lombok.Data;

@Data
public class UsuarioDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idUsuario;
    private String nombre;
    private String correo;
    private String contrasena;
    private Integer idSucursal;
    private Rol rol; 
    
}

