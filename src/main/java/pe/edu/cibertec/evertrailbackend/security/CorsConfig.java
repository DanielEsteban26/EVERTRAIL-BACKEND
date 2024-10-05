package pe.edu.cibertec.evertrailbackend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:4200"); // Permitir solicitudes desde http://localhost:4200
        corsConfiguration.addAllowedHeader("*"); // Permitir todos los encabezados
        corsConfiguration.addAllowedMethod("*"); // Permitir todos los métodos HTTP (GET, POST, PUT, DELETE, etc.)
        corsConfiguration.setAllowCredentials(true); // Permitir el envío de credenciales (cookies, cabeceras de autorización)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration); // Aplicar esta configuración a todas las rutas
        return new CorsFilter(source);
    }
}