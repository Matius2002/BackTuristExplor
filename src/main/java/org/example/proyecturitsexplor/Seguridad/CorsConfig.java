package org.example.proyecturitsexplor.Seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    /**
     * Configura el filtro CORS para permitir solicitudes desde orígenes específicos.
     * Este filtro es necesario para habilitar la comunicación entre el cliente (por ejemplo, Angular) y el servidor Spring Boot
     * en diferentes puertos o dominios.
     *
     * @return Un filtro CORS configurado.
     */
    @Bean
    public CorsFilter corsFilter() {
        // Se crea un objeto que contiene las configuraciones CORS para la URL.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // Se crea la configuración CORS
        CorsConfiguration config = new CorsConfiguration();

        // Permitir solicitudes desde la interfaz Angular (agregue o modifique las URLs según la configuración de su proyecto).
        // Se permiten múltiples orígenes (direcciones) que son posibles desde los cuales el cliente puede hacer solicitudes.
        config.addAllowedOrigin("http://localhost"); // Permite el acceso desde http://localhost
        config.addAllowedOrigin("http://localhost:80"); // Permite el acceso desde http://localhost:80
        config.addAllowedOrigin("http://localhost:4200"); // Permite el acceso desde http://localhost:4200 (puerto típico de Angular)
        config.addAllowedOrigin("http://localhost:8082"); // Permite el acceso desde http://localhost:8082
        config.addAllowedOrigin("http://localhost:8080"); // Permite el acceso desde http://localhost:8080
        config.addAllowedOrigin("http://localhost:52177"); // Permite el acceso desde http://localhost:52177

        // Permitir métodos HTTP comunes (GET, POST, PUT, DELETE, etc.)
        config.addAllowedMethod("*"); // Se permiten todos los métodos HTTP

        // Permitir encabezados HTTP comunes (como Content-Type, Authorization, etc.)
        config.addAllowedHeader("*"); // Se permiten todos los encabezados

        // Permitir el envío de credenciales (como cookies, autenticación de sesión, etc.)
        config.setAllowCredentials(true);

        // Registrar la configuración CORS para todas las URL en la aplicación
        source.registerCorsConfiguration("/**", config);

        // Retorna el filtro CORS configurado
        return new CorsFilter((CorsConfigurationSource) source);
    }
}