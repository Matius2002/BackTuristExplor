package org.example.proyecturitsexplor.Seguridad;

import org.example.proyecturitsexplor.Excepciones.UserNotFoundException;
import org.example.proyecturitsexplor.Repositorios.UserRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityBeansInjector {

    @Autowired
    private AuthenticationConfiguration authConfig;  // Inyección de la configuración de autenticación de Spring Security
    @Autowired
    private UserRepositorio userRep;  // Repositorio para interactuar con los usuarios en la base de datos

    /**
     * Bean para el AuthenticationManager, encargado de manejar la autenticación de los usuarios.
     *
     * @return Un AuthenticationManager para manejar las autenticaciones de Spring Security.
     * @throws Exception Excepción en caso de que haya un problema con la configuración.
     */
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return this.authConfig.getAuthenticationManager();
    }

    /**
     * Bean para el PasswordEncoder, utilizado para codificar las contraseñas de los usuarios.
     *
     * @return Un PasswordEncoder utilizando BCrypt para la codificación de contraseñas.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Se usa BCrypt para encriptar las contraseñas.
    }

    /**
     * Bean para el UserDetailsService, que se utiliza para cargar los detalles de un usuario desde la base de datos.
     *
     * @return Un UserDetailsService que obtiene los detalles del usuario desde el repositorio de usuarios.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return (String email) -> {
            // Busca al usuario en la base de datos por su email.
            return this.userRep.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException(email));  // Lanza una excepción si el usuario no se encuentra.
        };
    }

    /**
     * Bean para el AuthenticationProvider, que se encarga de autenticar a los usuarios.
     *
     * @return Un AuthenticationProvider que usa DaoAuthenticationProvider para la autenticación basada en la base de datos.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());  // Establece el codificador de contraseñas.
        authProvider.setUserDetailsService(userDetailsService());  // Establece el servicio para cargar detalles del usuario.
        return authProvider;  // Retorna el proveedor de autenticación configurado.
    }
}