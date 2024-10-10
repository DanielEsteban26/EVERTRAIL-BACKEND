package pe.edu.cibertec.evertrailbackend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.evertrailbackend.dto.UsuarioDTO;
import pe.edu.cibertec.evertrailbackend.entidad.Usuario;
import pe.edu.cibertec.evertrailbackend.serviceImp.CuentaService;
import pe.edu.cibertec.evertrailbackend.utils.MensajeResponse;
import pe.edu.cibertec.evertrailbackend.utils.ModeloNotFoundException;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/cuenta")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<?> getCuenta(@RequestParam String nombreUsuario) {
        try {
            Usuario usuario = cuentaService.getCuenta(nombreUsuario);
            if (usuario == null) {
                throw new ModeloNotFoundException("Usuario no encontrado: " + nombreUsuario);
            }
            UsuarioDTO usuarioDTO = mapper.map(usuario, UsuarioDTO.class);
            return new ResponseEntity<>(MensajeResponse.builder().mensaje("Cuenta encontrada").object(usuarioDTO).build(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(MensajeResponse.builder().mensaje("Error al buscar la cuenta").object(null).build());
        }
    }

    @PutMapping
    public ResponseEntity<?> updateCuenta(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            Usuario usuario = mapper.map(usuarioDTO, Usuario.class);
            Usuario cuentaActualizada = cuentaService.updateCuenta(usuario);
            UsuarioDTO cuentaActualizadaDTO = mapper.map(cuentaActualizada, UsuarioDTO.class);
            return new ResponseEntity<>(MensajeResponse.builder().mensaje("Cuenta actualizada exitosamente").object(cuentaActualizadaDTO).build(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(MensajeResponse.builder().mensaje("Error al actualizar la cuenta").object(null).build());
        }
    }
}
