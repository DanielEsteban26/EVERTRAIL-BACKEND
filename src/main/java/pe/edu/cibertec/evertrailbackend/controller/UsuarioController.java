package pe.edu.cibertec.evertrailbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.evertrailbackend.entidad.Usuario;
import pe.edu.cibertec.evertrailbackend.serviceImp.UsuarioService;

import java.util.Set;

@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/api/usuarios") // Mapea las solicitudes HTTP a /api/usuarios
public class UsuarioController {

    @Autowired // Inyección de dependencias de Spring
    private UsuarioService usuarioService;

    @GetMapping("/listar") // Mapea las solicitudes GET a /api/usuarios/listar
    public ResponseEntity<Set<Usuario>> getAllUsuarios() {
        try {
            return ResponseEntity.ok(usuarioService.listar()); // Retorna todos los usuarios
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build(); // Retorna un error 500 si ocurre una excepción
        }
    }

    @GetMapping("/buscar/{id}") // Mapea las solicitudes GET a /api/usuarios/buscar/{id}
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(usuarioService.buscar(id)); // Retorna el usuario con el ID especificado
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // Retorna un error 404 si no se encuentra el usuario
        }
    }

    @PostMapping("/registrar") // Mapea las solicitudes POST a /api/usuarios/registrar
    public ResponseEntity<String> createUsuario(@RequestBody Usuario usuario) {
        try {
            // Verificar si el nombre de usuario ya existe
            if (usuarioService.listar().stream().anyMatch(u -> u.getNombreUsuario().equals(usuario.getNombreUsuario()))) {
                return ResponseEntity.badRequest().body("El nombre de usuario ya existe.");
            }

            // Verificar si el correo ya existe
            if (usuarioService.listar().stream().anyMatch(u -> u.getCorreo().equals(usuario.getCorreo()))) {
                return ResponseEntity.badRequest().body("El correo ya existe.");
            }

            // Encriptar la contraseña
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));

            // Registrar el nuevo usuario
            usuarioService.registrar(usuario);
            return ResponseEntity.ok("Usuario registrado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Ocurrió un error al registrar el usuario.");
        }
    }

    @PutMapping("/actualizar/{id}") // Mapea las solicitudes PUT a /api/usuarios/actualizar/{id}
    public ResponseEntity<Usuario> updateUsuario(@PathVariable("id") Long id, @RequestBody Usuario usuario) {
        try {
            usuario.setId(id); // Establece el ID del usuario
            return ResponseEntity.ok(usuarioService.actualizar(usuario)); // Actualiza el usuario
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // Retorna un error 404 si no se encuentra el usuario
        }
    }

    @DeleteMapping("/eliminar/{id}") // Mapea las solicitudes DELETE a /api/usuarios/eliminar/{id}
    public ResponseEntity<Void> deleteUsuario(@PathVariable("id") Long id) {
        try {
            usuarioService.eliminar(id); // Elimina el usuario con el ID especificado
            return ResponseEntity.noContent().build(); // Retorna un código 204 si la eliminación es exitosa
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // Retorna un error 404 si no se encuentra el usuario
        }
    }
}




