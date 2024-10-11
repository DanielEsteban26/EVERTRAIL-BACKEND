package pe.edu.cibertec.evertrailbackend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.evertrailbackend.dto.CarritoDTO;
import pe.edu.cibertec.evertrailbackend.entidad.Carrito;
import pe.edu.cibertec.evertrailbackend.serviceImp.CarritoService;
import pe.edu.cibertec.evertrailbackend.utils.MensajeResponse;
import pe.edu.cibertec.evertrailbackend.utils.ModeloNotFoundException;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/carritos")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;
    @Autowired
    private ModelMapper mapper;

    @GetMapping("/listar")
    public ResponseEntity<?> getAllCarritos() {
        try {
            Set<Carrito> lista = carritoService.listar();
            if (lista.isEmpty()) {
                return new ResponseEntity<>(MensajeResponse.builder().mensaje("No hay registros").object(null).build(), HttpStatus.OK);
            } else {
                List<CarritoDTO> listaDTO = lista.stream().map(m -> mapper.map(m, CarritoDTO.class)).collect(Collectors.toList());
                return new ResponseEntity<>(MensajeResponse.builder().mensaje("Existen registros").object(listaDTO).build(), HttpStatus.OK);
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> getCarritoById(@PathVariable("id") Long id) {
        try {
            Carrito carrito = carritoService.buscar(id);
            if (carrito == null) {
                throw new ModeloNotFoundException("ID NO ENCONTRADO : " + id);
            }
            CarritoDTO carritoDTO = mapper.map(carrito, CarritoDTO.class);
            return new ResponseEntity<>(MensajeResponse.builder().mensaje("Encontrado").object(carritoDTO).build(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<String> createCarrito(@Valid @RequestBody CarritoDTO carritoDTO) {
        try {
            Carrito carrito = mapper.map(carritoDTO, Carrito.class);
            carritoService.registrar(carrito);
            return ResponseEntity.ok("Carrito registrado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Ocurrió un error al registrar el carrito.");
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updateCarrito(@PathVariable("id") Long id, @Valid @RequestBody CarritoDTO carritoDTO) {
        try {
            Carrito carrito = mapper.map(carritoDTO, Carrito.class);
            carrito.setId(id);
            Carrito updatedCarrito = carritoService.actualizar(carrito);
            return new ResponseEntity<>(MensajeResponse.builder().mensaje("Actualizado correctamente").object(mapper.map(updatedCarrito, CarritoDTO.class)).build(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> deleteCarrito(@PathVariable("id") Long id) {
        try {
            carritoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
