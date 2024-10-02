package pe.edu.cibertec.evertrailbackend.security;

import org.springframework.beans.factory.annotation.Autowired; // Importa la anotación Autowired para la inyección de dependencias.
import org.springframework.context.annotation.Bean; // Importa la anotación Bean para definir un bean de Spring.
import org.springframework.context.annotation.Configuration; // Importa la anotación Configuration para marcar esta clase como una configuración de Spring.
import org.springframework.http.HttpMethod; // Importa la clase HttpMethod para trabajar con métodos HTTP.
import org.springframework.security.config.annotation.web.builders.HttpSecurity; // Importa la clase HttpSecurity para configurar la seguridad web.
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; // Importa la anotación EnableWebSecurity para habilitar la seguridad web.
import org.springframework.security.web.SecurityFilterChain; // Importa la clase SecurityFilterChain para definir la cadena de filtros de seguridad.
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // Importa la clase UsernamePasswordAuthenticationFilter para manejar la autenticación basada en nombre de usuario y contraseña.

import static pe.edu.cibertec.evertrailbackend.security.Constants.LOGIN_URL; // Importa la constante LOGIN_URL.

@EnableWebSecurity // Habilita la seguridad web.
@Configuration // Marca esta clase como una configuración de Spring.
public class WebSecurityConfig {

    @Autowired // Inyección de dependencias de Spring.
    JWTAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        // Permisos para Administrador
                        .requestMatchers("/api/usuarios/**").hasRole("Administrador")
                        .requestMatchers("/api/productos/**").hasRole("Administrador")
                        .requestMatchers("/api/categorias/**").hasRole("Administrador")
                        .requestMatchers("/api/pedidos/**").hasRole("Administrador")
                        .requestMatchers("/api/resenas/**").hasRole("Administrador")
                        .requestMatchers("/api/imagenes-producto/**").hasRole("Administrador")
                        .requestMatchers("/api/metodos-pago/**").hasRole("Administrador")
                        .requestMatchers("/api/direcciones-envio/**").hasRole("Administrador")
                        // Permisos para Usuario No Registrado
                        .requestMatchers("/api/productos/**", "/api/categorias/**").permitAll()
                        // Permisos para Cliente Registrado
                        .requestMatchers("/api/pedidos/**").hasRole("Cliente")
                        .requestMatchers("/api/resenas/**").hasRole("Cliente")
                        .requestMatchers("/api/cuenta/**").hasRole("Cliente")
                        .requestMatchers("/api/metodos-pago/**").hasRole("Cliente")
                        .requestMatchers("/api/direcciones-envio/**").hasRole("Cliente")
                        // Permitir acceso a la página de login
                        .requestMatchers(LOGIN_URL).permitAll()
                        // Cualquier otra solicitud requiere autenticación
                        .anyRequest().authenticated())
                .addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}