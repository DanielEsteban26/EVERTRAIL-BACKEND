package pe.edu.cibertec.evertrailbackend.security;

import io.jsonwebtoken.*; // Importa las clases necesarias para trabajar con JWT.
import jakarta.servlet.FilterChain; // Importa la clase FilterChain para manejar la cadena de filtros.
import jakarta.servlet.ServletException; // Importa la clase ServletException para manejar excepciones de servlets.
import jakarta.servlet.http.HttpServletRequest; // Importa la clase HttpServletRequest para manejar solicitudes HTTP.
import jakarta.servlet.http.HttpServletResponse; // Importa la clase HttpServletResponse para manejar respuestas HTTP.
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; // Importa la clase UsernamePasswordAuthenticationToken para manejar la autenticación.
import org.springframework.security.core.authority.SimpleGrantedAuthority; // Importa la clase SimpleGrantedAuthority para manejar las autoridades.
import org.springframework.security.core.context.SecurityContextHolder; // Importa la clase SecurityContextHolder para manejar el contexto de seguridad.
import org.springframework.stereotype.Component; // Importa la anotación Component para marcar esta clase como un componente de Spring.
import org.springframework.web.filter.OncePerRequestFilter; // Importa la clase OncePerRequestFilter para asegurar que el filtro se ejecute una vez por solicitud.

import java.io.IOException; // Importa la clase IOException para manejar excepciones de entrada/salida.
import java.util.List; // Importa la clase List para manejar listas.
import java.util.stream.Collectors; // Importa la clase Collectors para recolectar los resultados de un stream.

import static pe.edu.cibertec.evertrailbackend.security.Constants.*; // Importa todas las constantes definidas en la clase Constants.

@Component // Marca esta clase como un componente de Spring.
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    // Método para obtener las reclamaciones del JWT usando la clave de firma.
    private Claims setSigningKey(HttpServletRequest request) {
        // Obtiene el token JWT del encabezado de autorización y elimina el prefijo "Bearer ".
        String jwtToken = request.getHeader(HEADER_AUTHORIZACION_KEY).replace(TOKEN_BEARER_PREFIX, "");
        // Analiza el token JWT y devuelve las reclamaciones.
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // Establece la clave de firma.
                .build()
                .parseClaimsJws(jwtToken) // Analiza el token JWT.
                .getBody(); // Obtiene el cuerpo de las reclamaciones.
    }

    // Método para establecer la autenticación en el contexto de seguridad.
    private void setAuthentication(Claims claims) {
        // Obtiene los roles de las reclamaciones.
        List<String> roles = (List<String>) claims.get("roles");

        // Crea un token de autenticación con el sujeto y los roles.
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
                        roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

        // Establece el token de autenticación en el contexto de seguridad.
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    // Método para verificar si el JWT es válido.
    private boolean isJWTValid(HttpServletRequest request, HttpServletResponse response) {
        // Obtiene el encabezado de autorización.
        String authorizationHeader = request.getHeader(HEADER_AUTHORIZACION_KEY);
        // Verifica que el encabezado no sea nulo y que comience con el prefijo "Bearer ".
        return authorizationHeader != null && authorizationHeader.startsWith(TOKEN_BEARER_PREFIX);
    }

    // Método que se ejecuta una vez por solicitud para filtrar las solicitudes HTTP.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // Verifica si el JWT es válido.
            if (isJWTValid(request, response)) {
                // Obtiene las reclamaciones del JWT.
                Claims claims = setSigningKey(request);
                // Si las reclamaciones contienen roles, establece la autenticación.
                if (claims.get("roles") != null) {
                    setAuthentication(claims);
                } else {
                    // Si no hay roles, limpia el contexto de seguridad.
                    SecurityContextHolder.clearContext();
                }
            } else {
                // Si el JWT no es válido, limpia el contexto de seguridad.
                SecurityContextHolder.clearContext();
            }
            // Continúa con la cadena de filtros.
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            // Si ocurre una excepción relacionada con el JWT, establece el estado de la respuesta a 403 (Prohibido).
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        }
    }
}