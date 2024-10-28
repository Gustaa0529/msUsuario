package com.example.consecionaria.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRol;

    private String nombre;

    @OneToMany(mappedBy = "rol")
    private List<Usuario> usuarios;
    
    public Rol() {
        // Constructor por defecto
    }

    public Rol(int idRol) {
        this.idRol = idRol;
    }
}
