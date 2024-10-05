package pe.edu.cibertec.evertrailbackend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pe.edu.cibertec.evertrailbackend.serviceImp.TokenRevocationService;

import static org.springframework.security.config.Customizer.withDefaults;
import static pe.edu.cibertec.evertrailbackend.security.Constants.LOGIN_URL;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    @Autowired
    JWTAuthorizationFilter jwtAuthorizationFilter;

    @Autowired
    private TokenRevocationService tokenRevocationService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults()) // Habilitar CORS
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        // Permisos para Administrador
                        .requestMatchers("/api/usuarios/**").hasRole("Administrador")
                        .requestMatchers("/api/categorias/**").hasRole("Administrador")
                        .requestMatchers("/api/pedidos/**").hasRole("Administrador")
                        .requestMatchers("/api/resenas/**").hasRole("Administrador")
                        .requestMatchers("/api/imagenes-producto/**").hasRole("Administrador")
                        .requestMatchers("/api/metodos-pago/**").hasRole("Administrador")
                        .requestMatchers("/api/direcciones-envio/**").hasRole("Administrador")
                        // Permisos para Usuario No Registrado
                        .requestMatchers(HttpMethod.GET, "/api/productos/**", "/api/categorias/**").permitAll()
                        // Permisos para Cliente Registrado
                        .requestMatchers("/api/pedidos/**").hasRole("Cliente")
                        .requestMatchers("/api/resenas/**").hasRole("Cliente")
                        .requestMatchers("/api/cuenta/**").hasRole("Cliente")
                        .requestMatchers("/api/metodos-pago/**").hasRole("Cliente")
                        .requestMatchers("/api/direcciones-envio/**").hasRole("Cliente")
                        // Permitir acceso a la página de login y logout
                        .requestMatchers(LOGIN_URL, "/user/logout").permitAll()
                        // Cualquier otra solicitud requiere autenticación
                        .anyRequest().authenticated())
                .addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}