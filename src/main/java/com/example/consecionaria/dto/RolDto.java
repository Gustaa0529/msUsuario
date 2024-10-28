package com.example.consecionaria.dto;

import lombok.Data;
import java.util.List;

@Data
public class RolDto {
	
    private int idRol;
    private String nombre;
    private List<UsuarioDto> usuarios;

}
