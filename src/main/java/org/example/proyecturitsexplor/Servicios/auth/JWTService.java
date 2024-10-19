package org.example.proyecturitsexplor.Servicios.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.proyecturitsexplor.Entidades.Usuarios;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Map;

@Service
public class JWTService {

    // Valor de la expiración en minutos para el JWT, obtenido desde el archivo de configuración
    @Value("${security.jwt.expiration-in-minutes}")
    private Long EXPIRATION_IN_MINUTES;

    // Clave secreta para la firma del JWT, que se obtiene de un SecretKey (asegúrate de que no sea expuesta)
    private final String secretKey = new SecretKey().secretKey;

    /*Métodos*/

    /**
     * Genera un JWT (JSON Web Token) para un usuario determinado con los claims proporcionados.
     *
     * @param usuario El usuario para el cual se generará el JWT.
     * @param claims Un mapa de claims adicionales que se agregarán al JWT (como roles, permisos, etc.).
     * @return El JWT generado como una cadena.
     */
    public String generarToken(Usuarios usuario, Map<String, Object> claims) {
        // Fecha de emisión del token, que es la fecha y hora actuales
        Date issuedAt = new Date(System.currentTimeMillis());

        // Fecha de expiración del token, calculada con base en la configuración de expiración (en minutos)
        Date expiration = new Date((EXPIRATION_IN_MINUTES * 60 * 1000) + issuedAt.getTime());

        // Construcción del token JWT usando el builder de JJWT
        return Jwts.builder()
                .header().type("JWT") // Especifica que el tipo de token es JWT
                .and()
                .subject(usuario.getEmail()) // Establece el correo electrónico del usuario como el "subject" del token
                .issuedAt(issuedAt) // Establece la fecha de emisión del token
                .expiration(expiration) // Establece la fecha de expiración del token
                .claims(claims) // Agrega los claims personalizados del usuario
                .signWith(generarKey(), SignatureAlgorithm.HS256) // Firma el token usando la clave secreta y el algoritmo HS256
                .compact(); // Compone el token JWT en una cadena compacta
    }

    /**
     * Genera la clave secreta para firmar el JWT.
     * La clave secreta se decodifica desde una cadena en base64 y se convierte en un objeto SecretKey.
     *
     * @return La clave secreta generada para la firma del JWT.
     */
    private javax.crypto.SecretKey generarKey() {
        // Decodifica la clave secreta en base64 y la convierte en un array de bytes
        byte[] secretAsBytes = Decoders.BASE64.decode(secretKey);

        // Genera una clave secreta para la firma utilizando el algoritmo HMAC-SHA
        return Keys.hmacShaKeyFor(secretAsBytes);
    }

    /**
     * Extrae el nombre de usuario (email) desde un JWT.
     *
     * @param jwt El JWT del cual se extraerá el nombre de usuario.
     * @return El nombre de usuario (email) extraído del JWT.
     */
    public String extractUsername(String jwt) {
        // Extrae los claims del JWT y obtiene el "subject", que en este caso es el email del usuario
        return extractAllClaims(jwt).getSubject();
    }

    /**
     * Extrae todos los claims del JWT. Los claims contienen la información personalizada del token,
     * como el email del usuario, roles, etc.
     *
     * @param jwt El JWT del cual se extraerán los claims.
     * @return Los claims extraídos del JWT.
     */
    private Claims extractAllClaims(String jwt) {
        // El parser se usa para verificar la firma del JWT con la clave secreta
        // Después de la validación, se extraen los claims del JWT
        return Jwts.parser()
                .setSigningKey(generarKey()) // Establece la clave secreta para la validación
                .build() // Construye el parser
                .parseClaimsJws(jwt) // Parseamos el JWT con los claims firmados
                .getBody(); // Extrae el cuerpo de los claims
    }

    /**
     * Valida si el token JWT es válido. Este metodo no realiza verificaciones adicionales en este ejemplo,
     * pero normalmente se incluiría la lógica para validar el token.
     *
     * @param jwt El JWT a validar.
     * @return true si el token es válido, false si no lo es.
     */
    public boolean validateToken(String jwt) {
        // En este caso, la validación siempre devuelve true, pero puedes añadir aquí lógica para comprobar expiraciones o revocaciones
        return true;
    }

    /**
     * Extrae la fecha de expiración de un token JWT.
     *
     * @param token El JWT del cual se extraerá la fecha de expiración.
     * @return La fecha de expiración del JWT.
     */
    public Date extractExpiration(String token) {
        // Extrae los claims del JWT y obtiene la fecha de expiración
        return extractAllClaims(token).getExpiration();
    }
}
