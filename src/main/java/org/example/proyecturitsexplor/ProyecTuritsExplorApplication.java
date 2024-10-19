package org.example.proyecturitsexplor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  // Anotación que marca la clase como la aplicación Spring Boot principal
public class ProyecTuritsExplorApplication {

	// Metodo principal que arranca la aplicación Spring Boot
	public static void main(String[] args) {
		// Llama a SpringApplication.run para iniciar la aplicación Spring Boot
		SpringApplication.run(ProyecTuritsExplorApplication.class, args);
	}
}

