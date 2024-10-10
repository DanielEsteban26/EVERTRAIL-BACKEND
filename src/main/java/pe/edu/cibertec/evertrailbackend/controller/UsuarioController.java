package pe.edu.cibertec.evertrailbackend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.evertrailbackend.dto.UsuarioDTO;
import pe.edu.cibertec.evertrailbackend.entidad.Role;
import pe.edu.cibertec.evertrailbackend.entidad.Usuario;
import pe.edu.cibertec.evertrailbackend.serviceImp.RoleService;
import pe.edu.cibertec.evertrailbackend.serviceImp.UsuarioService;
import pe.edu.cibertec.evertrailbackend.utils.MensajeResponse;
import pe.edu.cibertec.evertrailbackend.utils.ModeloNotFoundException;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/listar")
    public ResponseEntity<?> getAllUsuarios() {
        try {
            Set<Usuario> lista = usuarioService.listar();
            if (lista.isEmpty()) {
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("No hay registros")
                        .object(null)
                        .build(), HttpStatus.OK);
            } else {
                List<UsuarioDTO> listaDTO = lista.stream()
                        .map(m -> mapper.map(m, UsuarioDTO.class))
                        .collect(Collectors.toList());
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("Existen registros")
                        .object(listaDTO)
                        .build(), HttpStatus.OK);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MensajeResponse.builder()
                            .mensaje("Error al listar usuarios")
                            .object(null)
                            .build());
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> getUsuarioById(@PathVariable("id") Long id) {
        try {
            Usuario usuario = usuarioService.buscar(id);
            if (usuario == null) {
                throw new ModeloNotFoundException("ID NO ENCONTRADO : " + id);
            }
            UsuarioDTO usuarioDTO = mapper.map(usuario, UsuarioDTO.class);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Usuario encontrado")
                    .object(usuarioDTO)
                    .build(), HttpStatus.OK);
        } catch (ModeloNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MensajeResponse.builder()
                            .mensaje(e.getMessage())
                            .object(null)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MensajeResponse.builder()
                            .mensaje("Error al buscar el usuario")
                            .object(null)
                            .build());
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> createUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        try {
            // Verificar si el nombre de usuario ya existe
            if (usuarioService.listar().stream().anyMatch(u -> u.getNombreUsuario().equals(usuarioDTO.getNombreUsuario()))) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(MensajeResponse.builder()
                                .mensaje("El nombre de usuario ya existe.")
                                .object(null)
                                .build());
            }

            // Verificar si el correo ya existe
            if (usuarioService.listar().stream().anyMatch(u -> u.getCorreo().equals(usuarioDTO.getCorreo()))) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(MensajeResponse.builder()
                                .mensaje("El correo ya existe.")
                                .object(null)
                                .build());
            }

            // Encriptar la contraseña
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String contraseniaCodificada = passwordEncoder.encode(usuarioDTO.getContrasenia());
            usuarioDTO.setContrasenia(contraseniaCodificada); // Codificando la contraseña original

            // Convertir UsuarioDTO a Usuario
            Usuario usuario = mapper.map(usuarioDTO, Usuario.class);

            // Registrar el nuevo usuario
            Usuario usuarioGuardado = usuarioService.registrar(usuario);

            // Convertir el Usuario registrado a DTO para la respuesta
            UsuarioDTO usuarioGuardadoDTO = mapper.map(usuarioGuardado, UsuarioDTO.class);

            // Retornar la respuesta exitosa
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(MensajeResponse.builder()
                            .mensaje("Usuario registrado exitosamente")
                            .object(usuarioGuardadoDTO)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MensajeResponse.builder()
                            .mensaje("Ocurrió un error al registrar el usuario")
                            .object(null)
                            .build());
        }
    }


    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updateUsuario(@PathVariable("id") Long id, @Valid @RequestBody UsuarioDTO usuarioDTO) {
        try {
            // Buscar al usuario existente por su ID
            Usuario usuario = usuarioService.buscar(id);
            if (usuario == null) {
                throw new ModeloNotFoundException("ID NO ENCONTRADO : " + id);
            }

            // Encriptar la nueva contraseña solo si el DTO contiene una contraseña
            if (usuarioDTO.getContrasenia() != null && !usuarioDTO.getContrasenia().isEmpty()) {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String contraseniaCodificada = passwordEncoder.encode(usuarioDTO.getContrasenia());
                usuarioDTO.setContrasenia(contraseniaCodificada); // Codifica la contraseña
            } else {
                // Mantener la contraseña actual si no se envía una nueva
                usuarioDTO.setContrasenia(usuario.getContrasenia());
            }

            // Establecer el ID del usuario a actualizar
            usuarioDTO.setId(id);

            // Convertir el UsuarioDTO actualizado a la entidad Usuario
            Usuario updatedUsuario = mapper.map(usuarioDTO, Usuario.class);

            // Actualizar el usuario en la base de datos
            Usuario usuarioActualizado = usuarioService.actualizar(updatedUsuario);

            // Convertir el Usuario actualizado a DTO para la respuesta
            UsuarioDTO usuarioActualizadoDTO = mapper.map(usuarioActualizado, UsuarioDTO.class);

            // Retornar la respuesta exitosa
            return ResponseEntity.status(HttpStatus.OK)
                    .body(MensajeResponse.builder()
                            .mensaje("Usuario actualizado correctamente")
                            .object(usuarioActualizadoDTO)
                            .build());
        } catch (ModeloNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MensajeResponse.builder()
                            .mensaje(e.getMessage())
                            .object(null)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MensajeResponse.builder()
                            .mensaje("Error al actualizar el usuario")
                            .object(null)
                            .build());
        }
    }


    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable("id") Long id) {
        try {
            usuarioService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (ModeloNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MensajeResponse.builder()
                            .mensaje(e.getMessage())
                            .object(null)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MensajeResponse.builder()
                            .mensaje("Error al eliminar el usuario")
                            .object(null)
                            .build());
        }
    }
}
