package pe.edu.cibertec.evertrailbackend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.evertrailbackend.dto.MetodoPagoDTO;
import pe.edu.cibertec.evertrailbackend.entidad.MetodoPago;
import pe.edu.cibertec.evertrailbackend.serviceImp.MetodoPagoService;
import pe.edu.cibertec.evertrailbackend.utils.MensajeResponse;
import pe.edu.cibertec.evertrailbackend.utils.ModeloNotFoundException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/metodos-pago")
public class MetodoPagoController {

    @Autowired
    private MetodoPagoService metodoPagoService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/listar")
    public ResponseEntity<?> getAllMetodosPago() {
        try {
            Set<MetodoPago> lista = metodoPagoService.listar();
            if (lista.isEmpty()) {
                return new ResponseEntity<>(MensajeResponse.builder().mensaje("No hay registros").object(null).build(), HttpStatus.OK);
            } else {
                List<MetodoPagoDTO> listaDTO = lista.stream()
                        .map(m -> mapper.map(m, MetodoPagoDTO.class))
                        .collect(Collectors.toList());
                return new ResponseEntity<>(MensajeResponse.builder().mensaje("Existen registros").object(listaDTO).build(), HttpStatus.OK);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MensajeResponse.builder().mensaje("Error al listar los métodos de pago").object(null).build());
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> getMetodoPagoById(@PathVariable("id") Long id) {
        try {
            MetodoPago metodoPago = metodoPagoService.buscar(id);
            if (metodoPago == null) {
                throw new ModeloNotFoundException("ID NO ENCONTRADO : " + id);
            }
            MetodoPagoDTO metodoPagoDTO = mapper.map(metodoPago, MetodoPagoDTO.class);
            return new ResponseEntity<>(MensajeResponse.builder().mensaje("Método de pago encontrado").object(metodoPagoDTO).build(), HttpStatus.OK);
        } catch (ModeloNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MensajeResponse.builder().mensaje(e.getMessage()).object(null).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MensajeResponse.builder().mensaje("Error al buscar el método de pago").object(null).build());
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> createMetodoPago(@RequestBody MetodoPagoDTO metodoPagoDTO) {
        try {
            MetodoPago metodoPago = mapper.map(metodoPagoDTO, MetodoPago.class);
            MetodoPago metodoPagoGuardado = metodoPagoService.registrar(metodoPago);
            MetodoPagoDTO metodoPagoGuardadoDTO = mapper.map(metodoPagoGuardado, MetodoPagoDTO.class);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Método de pago registrado exitosamente")
                    .object(metodoPagoGuardadoDTO)
                    .build(), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MensajeResponse.builder().mensaje("Error al registrar el método de pago").object(null).build());
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updateMetodoPago(@PathVariable("id") Long id, @RequestBody MetodoPagoDTO metodoPagoDTO) {
        try {
            MetodoPago metodoPago = mapper.map(metodoPagoDTO, MetodoPago.class);
            metodoPago.setId(id);
            MetodoPago metodoPagoActualizado = metodoPagoService.actualizar(metodoPago);
            MetodoPagoDTO metodoPagoActualizadoDTO = mapper.map(metodoPagoActualizado, MetodoPagoDTO.class);
            return new ResponseEntity<>(MensajeResponse.builder().mensaje("Método de pago actualizado exitosamente").object(metodoPagoActualizadoDTO).build(), HttpStatus.OK);
        } catch (ModeloNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MensajeResponse.builder().mensaje(e.getMessage()).object(null).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MensajeResponse.builder().mensaje("Error al actualizar el método de pago").object(null).build());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> deleteMetodoPago(@PathVariable("id") Long id) {
        try {
            metodoPagoService.eliminar(id);
            return new ResponseEntity<>(MensajeResponse.builder().mensaje("Método de pago eliminado exitosamente").object(null).build(), HttpStatus.NO_CONTENT);
        } catch (ModeloNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MensajeResponse.builder().mensaje(e.getMessage()).object(null).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MensajeResponse.builder().mensaje("Error al eliminar el método de pago").object(null).build());
        }
    }
}
