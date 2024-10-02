package pe.edu.cibertec.evertrailbackend.security;

import io.jsonwebtoken.Jwts; // Importa la clase Jwts de la biblioteca io.jsonwebtoken para crear y manipular JWTs.
import io.jsonwebtoken.SignatureAlgorithm; // Importa la clase SignatureAlgorithm para especificar el algoritmo de firma.
import org.springframework.context.annotation.Configuration; // Importa la anotación Configuration para marcar esta clase como una configuración de Spring.
import org.springframework.security.core.GrantedAuthority; // Importa la interfaz GrantedAuthority que representa una autoridad otorgada a un Authentication.
import org.springframework.security.core.authority.AuthorityUtils; // Importa la clase AuthorityUtils para trabajar con listas de GrantedAuthority.

import java.util.Date; // Importa la clase Date para trabajar con fechas.
import java.util.List; // Importa la clase List para trabajar con listas.
import java.util.stream.Collectors; // Importa la clase Collectors para recolectar los resultados de un stream.

import static pe.edu.cibertec.evertrailbackend.security.Constants.*; // Importa todas las constantes definidas en la clase Constants.

@Configuration // Marca esta clase como una configuración de Spring.
public class JWTAuthenticationConfig {

    // Método para generar un token JWT basado en el nombre de usuario y los roles.
    public String getJWTToken(String username, List<String> roles) {
        // Convierte la lista de roles en una lista de GrantedAuthority.
        List<GrantedAuthority> grantedAuthorities = roles.stream()
                .map(role -> "ROLE_" + role) // Agrega el prefijo "ROLE_" a cada rol.
                .map(AuthorityUtils::commaSeparatedStringToAuthorityList) // Convierte cada rol en una lista de GrantedAuthority.
                .flatMap(List::stream) // Aplana la lista de listas en una sola lista.
                .collect(Collectors.toList()); // Recolecta los resultados en una lista.

        // Construye el token JWT.
        String token = Jwts
                .builder() // Inicia la construcción del token.
                .setId(username) // Establece el ID del token como el nombre de usuario.
                .setSubject(username) // Establece el sujeto del token como el nombre de usuario.
                .claim("roles", // Agrega una reclamación de roles al token.
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority) // Obtiene la autoridad de cada GrantedAuthority.
                                .collect(Collectors.toList())) // Recolecta las autoridades en una lista.
                .setIssuedAt(new Date(System.currentTimeMillis())) // Establece la fecha de emisión del token como la fecha y hora actual.
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME)) // Establece la fecha de expiración del token.
                .signWith(getSigningKey(), SignatureAlgorithm.HS512) // Firma el token con la clave de firma y el algoritmo HS512.
                .compact(); // Compacta el token en una cadena.

        // Devuelve el token con el prefijo "Bearer ".
        return TOKEN_BEARER_PREFIX + token;
    }

}