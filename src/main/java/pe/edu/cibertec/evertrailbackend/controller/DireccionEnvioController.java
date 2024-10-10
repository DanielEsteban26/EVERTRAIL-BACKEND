package pe.edu.cibertec.evertrailbackend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.evertrailbackend.dto.DireccionEnvioDTO;
import pe.edu.cibertec.evertrailbackend.entidad.DireccionEnvio;
import pe.edu.cibertec.evertrailbackend.serviceImp.DireccionEnvioService;
import pe.edu.cibertec.evertrailbackend.utils.MensajeResponse;
import pe.edu.cibertec.evertrailbackend.utils.ModeloNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/direcciones-envio")
public class DireccionEnvioController {

    @Autowired
    private DireccionEnvioService direccionEnvioService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/listar")
    public ResponseEntity<?> getAllDireccionesEnvio() {
        try {
            List<DireccionEnvioDTO> direccionesEnvioDTO = direccionEnvioService.listar()
                    .stream()
                    .map(direccion -> mapper.map(direccion, DireccionEnvioDTO.class))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(MensajeResponse.builder().mensaje("Direcciones de envío listadas correctamente").object(direccionesEnvioDTO).build(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(MensajeResponse.builder().mensaje("Error al listar las direcciones de envío").object(null).build());
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> getDireccionEnvioById(@PathVariable("id") Long id) {
        try {
            DireccionEnvio direccionEnvio = direccionEnvioService.buscar(id);
            if (direccionEnvio == null) {
                throw new ModeloNotFoundException("Dirección de envío no encontrada con ID: " + id);
            }
            DireccionEnvioDTO direccionEnvioDTO = mapper.map(direccionEnvio, DireccionEnvioDTO.class);
            return new ResponseEntity<>(MensajeResponse.builder().mensaje("Dirección de envío encontrada").object(direccionEnvioDTO).build(), HttpStatus.OK);
        } catch (ModeloNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MensajeResponse.builder().mensaje(e.getMessage()).object(null).build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(MensajeResponse.builder().mensaje("Error al buscar la dirección de envío").object(null).build());
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> createDireccionEnvio(@RequestBody DireccionEnvioDTO direccionEnvioDTO) {
        try {
            DireccionEnvio direccionEnvio = mapper.map(direccionEnvioDTO, DireccionEnvio.class);
            DireccionEnvio direccionRegistrada = direccionEnvioService.registrar(direccionEnvio);
            DireccionEnvioDTO direccionRegistradaDTO = mapper.map(direccionRegistrada, DireccionEnvioDTO.class);
            return new ResponseEntity<>(MensajeResponse.builder().mensaje("Dirección de envío registrada exitosamente").object(direccionRegistradaDTO).build(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(MensajeResponse.builder().mensaje("Error al registrar la dirección de envío").object(null).build());
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updateDireccionEnvio(@PathVariable("id") Long id, @RequestBody DireccionEnvioDTO direccionEnvioDTO) {
        try {
            DireccionEnvio direccionEnvio = mapper.map(direccionEnvioDTO, DireccionEnvio.class);
            direccionEnvio.setId(id);
            DireccionEnvio direccionActualizada = direccionEnvioService.actualizar(direccionEnvio);
            DireccionEnvioDTO direccionActualizadaDTO = mapper.map(direccionActualizada, DireccionEnvioDTO.class);
            return new ResponseEntity<>(MensajeResponse.builder().mensaje("Dirección de envío actualizada exitosamente").object(direccionActualizadaDTO).build(), HttpStatus.OK);
        } catch (ModeloNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MensajeResponse.builder().mensaje(e.getMessage()).object(null).build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(MensajeResponse.builder().mensaje("Error al actualizar la dirección de envío").object(null).build());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> deleteDireccionEnvio(@PathVariable("id") Long id) {
        try {
            direccionEnvioService.eliminar(id);
            return new ResponseEntity<>(MensajeResponse.builder().mensaje("Dirección de envío eliminada exitosamente").object(null).build(), HttpStatus.NO_CONTENT);
        } catch (ModeloNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MensajeResponse.builder().mensaje(e.getMessage()).object(null).build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(MensajeResponse.builder().mensaje("Error al eliminar la dirección de envío").object(null).build());
        }
    }
}
