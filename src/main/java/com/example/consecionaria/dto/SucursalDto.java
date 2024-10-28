package com.example.consecionaria.dto;

import lombok.Data;
import java.util.List;

@Data
public class SucursalDto {
	
    private int idSucursal;
    private String direccion;
    private List<UsuarioDto> usuarios; 

}