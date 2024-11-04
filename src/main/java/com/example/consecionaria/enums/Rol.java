package com.example.consecionaria.enums;

public enum Rol {
	
	    ADMIN("Admin"),
	    EMPLEADO("Empleado"),
	    PORTA_AUTOMOVILES("Porta Automóviles");

	    private String displayName;

	    Rol(String displayName) {
	        this.displayName = displayName;
	    }

	    public String getDisplayName() {
	        return displayName;
	    }
	
}
