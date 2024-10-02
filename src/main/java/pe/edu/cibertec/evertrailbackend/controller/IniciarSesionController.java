package pe.edu.cibertec.evertrailbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.cibertec.evertrailbackend.entidad.Usuario;
import pe.edu.cibertec.evertrailbackend.repositorio.UsuarioRepository;
import pe.edu.cibertec.evertrailbackend.security.JWTAuthenticationConfig;

import java.util.List;

@RestController
@RequestMapping("/user")
public class IniciarSesionController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JWTAuthenticationConfig jwtAuthenticationConfig;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario user) {
        if (user == null || user.getNombreUsuario() == null || user.getContraseña() == null) {
            return ResponseEntity.status(400).body("El cuerpo de la solicitud, el nombre de usuario y la contraseña son obligatorios");
        }

        Usuario userResult = usuarioRepository.findByNombreUsuario(user.getNombreUsuario());
        if (userResult == null) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(user.getContraseña(), userResult.getContraseña())) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }

        // Suponiendo que la entidad Usuario tiene un método getRoles() que devuelve una Lista<String> de roles
        List<String> roles = userResult.getRoles();
        String token = jwtAuthenticationConfig.getJWTToken(user.getNombreUsuario(), roles);
        return ResponseEntity.ok(token);
    }
}