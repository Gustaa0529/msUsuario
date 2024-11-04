package com.example.consecionaria.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.consecionaria.enums.Rol;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;

    private String nombre;
    private String correo;

    private String contrasena;

    @ManyToOne
    @JoinColumn(name = "idSucursal")
    private Sucursal sucursal;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    public void setContrasena(String contrasena) {
        this.contrasena = new BCryptPasswordEncoder().encode(contrasena);
    }
}