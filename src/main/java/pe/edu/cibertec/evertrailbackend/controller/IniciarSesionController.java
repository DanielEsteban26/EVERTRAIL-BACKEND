package pe.edu.cibertec.evertrailbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.evertrailbackend.entidad.Usuario;
import pe.edu.cibertec.evertrailbackend.repositorio.UsuarioRepository;
import pe.edu.cibertec.evertrailbackend.response.LoginResponse;
import pe.edu.cibertec.evertrailbackend.security.JWTAuthenticationConfig;
import pe.edu.cibertec.evertrailbackend.serviceImp.TokenRevocationService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user")
public class IniciarSesionController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JWTAuthenticationConfig jwtAuthenticationConfig;

    @Autowired
    private TokenRevocationService tokenRevocationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario user) {
        if (user == null || user.getNombreUsuario() == null || user.getContrasenia() == null) {
            return ResponseEntity.status(400).body("El cuerpo de la solicitud, el nombre de usuario y la contraseña son obligatorios");
        }

        Usuario userResult = usuarioRepository.findByNombreUsuario(user.getNombreUsuario());
        if (userResult == null) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(user.getContrasenia(), userResult.getContrasenia())) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }

        List<String> roles = userResult.getRoles();
        String token = jwtAuthenticationConfig.getJWTToken(user.getNombreUsuario(), roles);
        String rol = roles.isEmpty() ? "Cliente" : roles.get(0);

        LoginResponse loginResponse = new LoginResponse(token, rol, "Inicio de sesión exitoso");
        return ResponseEntity.ok(loginResponse);
    }

        @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody String token) {
        tokenRevocationService.revokeToken(token);
        return ResponseEntity.ok("{\"message\": \"Token revocado\"}");
    }
}