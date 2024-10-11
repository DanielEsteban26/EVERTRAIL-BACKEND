package pe.edu.cibertec.evertrailbackend.security;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modeloMapper(){
        return new ModelMapper();
    }
}