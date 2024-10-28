package com.example.consecionaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.consecionaria.entity.Rol;


@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
	
}

