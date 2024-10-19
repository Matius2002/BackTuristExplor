package org.example.proyecturitsexplor.Seguridad;

import org.example.proyecturitsexplor.Seguridad.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class HttpSecurityConfig {

    @Autowired
    private AuthenticationProvider authProvider;  // Proveedor de autenticación personalizado
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;  // Filtro de autenticación JWT
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;  // Maneja la respuesta cuando ocurre un error de autenticación
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;  // Maneja la respuesta cuando un usuario no tiene permisos para una acción

    /**
     * Configura la cadena de filtros de seguridad HTTP.
     *
     * @param http Configuración de HttpSecurity
     * @return La configuración de seguridad para las solicitudes HTTP
     * @throws Exception Excepción que puede ocurrir durante la configuración de seguridad
     */
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)  // Desactiva CSRF (Cross-Site Request Forgery)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Configura el manejo de sesiones como "stateless", lo que significa que el servidor no guardará información de sesión.
                .authenticationProvider(authProvider)  // Configura un proveedor de autenticación personalizado
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)  // Agrega el filtro JWT antes del filtro de autenticación de nombre de usuario y contraseña.
                .exceptionHandling(exceptionConfig -> {
                    exceptionConfig.authenticationEntryPoint(authenticationEntryPoint);  // Maneja la respuesta cuando ocurre un error de autenticación
                    exceptionConfig.accessDeniedHandler(accessDeniedHandler);  // Maneja la respuesta cuando un usuario no tiene los permisos necesarios para una operación
                })
                .build();  // Construye la configuración
    }
}







