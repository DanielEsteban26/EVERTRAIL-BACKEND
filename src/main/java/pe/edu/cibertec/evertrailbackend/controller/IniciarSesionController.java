package pe.edu.cibertec.evertrailbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.evertrailbackend.dto.UsuarioDTO;
import pe.edu.cibertec.evertrailbackend.entidad.Usuario;
import pe.edu.cibertec.evertrailbackend.repositorio.UsuarioRepository;
import pe.edu.cibertec.evertrailbackend.response.LoginResponse;
import pe.edu.cibertec.evertrailbackend.security.JWTAuthenticationConfig;
import pe.edu.cibertec.evertrailbackend.serviceImp.TokenRevocationService;
import pe.edu.cibertec.evertrailbackend.utils.MensajeResponse;
import pe.edu.cibertec.evertrailbackend.utils.ModeloNotFoundException;

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
    public ResponseEntity<?> login(@RequestBody UsuarioDTO userDTO) {
        if (userDTO == null || userDTO.getNombreUsuario() == null || userDTO.getContrasenia() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(MensajeResponse.builder().mensaje("El nombre de usuario y la contraseña son obligatorios").object(null).build());
        }

        Usuario userResult = usuarioRepository.findByNombreUsuario(userDTO.getNombreUsuario());
        if (userResult == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MensajeResponse.builder().mensaje("Usuario no encontrado").object(null).build());
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(userDTO.getContrasenia(), userResult.getContrasenia())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(MensajeResponse.builder().mensaje("Credenciales inválidas").object(null).build());
        }

        List<String> roles = userResult.getRoles();
        String token = jwtAuthenticationConfig.getJWTToken(userResult.getNombreUsuario(), roles);
        String rol = roles.isEmpty() ? "Cliente" : roles.get(0);

        LoginResponse loginResponse = new LoginResponse(token, rol, "OK, " + userResult.getNombreUsuario() + " logueado");
        return new ResponseEntity<>(MensajeResponse.builder().mensaje("Inicio de sesión exitoso").object(loginResponse).build(), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody String token) {
        try {
            tokenRevocationService.revokeToken(token);
            return new ResponseEntity<>(MensajeResponse.builder().mensaje("Token revocado exitosamente").object(null).build(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(MensajeResponse.builder().mensaje("Error al revocar el token").object(null).build());
        }
    }
}
