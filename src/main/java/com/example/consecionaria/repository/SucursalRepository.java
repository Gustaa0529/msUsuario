package com.example.consecionaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.consecionaria.entity.Sucursal;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Integer>{
	
}
