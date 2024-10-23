package pe.edu.cibertec.evertrailbackend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pe.edu.cibertec.evertrailbackend.serviceImp.TokenRevocationService;

import static org.springframework.security.config.Customizer.withDefaults;

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
                        // Permitir acceso a Swagger sin autenticación (incluyendo la ruta personalizada)
                        .requestMatchers("/doc/**","/v3/**").permitAll()

                        // Permisos para Administrador
                        .requestMatchers("/api/usuarios/**").hasRole("Administrador")
                        // producto y categoria no se pone porque se permiten para ambos roles

                        // metodo-pago no olvidarte para agregar como crud en el front como cliente
                        .requestMatchers("/api/metodos-pago/**").hasRole("Cliente")
                        // Permisos para Cliente Registrado
                        .requestMatchers("/api/carritos/**").permitAll()

                        // Permitir acceso a la página de login y logout
                        .requestMatchers( "/user/login", "/user/logout").permitAll()

                        // Cualquier otra solicitud requiere autenticación
                        .anyRequest().authenticated())
                .addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
