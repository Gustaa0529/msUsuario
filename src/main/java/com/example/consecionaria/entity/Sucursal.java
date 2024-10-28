package com.example.consecionaria.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "sucursales")
public class Sucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSucursal;

    private String direccion;

    @OneToMany(mappedBy = "sucursal")
    private List<Usuario> usuarios;
    
    public Sucursal() {
        // Constructor por defecto
    }

    public Sucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }
}
