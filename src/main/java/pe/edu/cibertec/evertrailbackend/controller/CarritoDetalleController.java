package pe.edu.cibertec.evertrailbackend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.evertrailbackend.dto.CarritoDetalleDTO;
import pe.edu.cibertec.evertrailbackend.entidad.CarritoDetalle;
import pe.edu.cibertec.evertrailbackend.serviceImp.CarritoDetalleService;
import pe.edu.cibertec.evertrailbackend.utils.MensajeResponse;
import pe.edu.cibertec.evertrailbackend.utils.ModeloNotFoundException;

import jakarta.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/carrito-detalles")
public class CarritoDetalleController {

    @Autowired
    private CarritoDetalleService carritoDetalleService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/listar")
    public ResponseEntity<?> getAllCarritoDetalles() {
        try {
            Set<CarritoDetalle> lista = carritoDetalleService.listar();
            if (lista.isEmpty()) {
                return new ResponseEntity<>(MensajeResponse.builder().mensaje("No hay registros").object(null).build(), HttpStatus.OK);
            } else {
                Set<CarritoDetalleDTO> listaDTO = lista.stream().map(m -> mapper.map(m, CarritoDetalleDTO.class)).collect(Collectors.toSet());
                return new ResponseEntity<>(MensajeResponse.builder().mensaje("Existen registros").object(listaDTO).build(), HttpStatus.OK);
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> getCarritoDetalleById(@PathVariable("id") Long id) {
        try {
            CarritoDetalle carritoDetalle = carritoDetalleService.buscar(id);
            if (carritoDetalle == null) {
                throw new ModeloNotFoundException("ID NO ENCONTRADO : " + id);
            }
            CarritoDetalleDTO carritoDetalleDTO = mapper.map(carritoDetalle, CarritoDetalleDTO.class);
            return new ResponseEntity<>(MensajeResponse.builder().mensaje("Encontrado").object(carritoDetalleDTO).build(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<String> createCarritoDetalle(@Valid @RequestBody CarritoDetalleDTO carritoDetalleDTO) {
        try {
            CarritoDetalle carritoDetalle = mapper.map(carritoDetalleDTO, CarritoDetalle.class);
            carritoDetalleService.registrar(carritoDetalle);
            return ResponseEntity.ok("Detalle del carrito registrado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Ocurrió un error al registrar el detalle del carrito.");
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updateCarritoDetalle(@PathVariable("id") Long id, @Valid @RequestBody CarritoDetalleDTO carritoDetalleDTO) {
        try {
            CarritoDetalle carritoDetalle = mapper.map(carritoDetalleDTO, CarritoDetalle.class);
            carritoDetalle.setId(id);
            CarritoDetalle updatedCarritoDetalle = carritoDetalleService.actualizar(carritoDetalle);
            return new ResponseEntity<>(MensajeResponse.builder().mensaje("Actualizado correctamente").object(mapper.map(updatedCarritoDetalle, CarritoDetalleDTO.class)).build(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> deleteCarritoDetalle(@PathVariable("id") Long id) {
        try {
            carritoDetalleService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
