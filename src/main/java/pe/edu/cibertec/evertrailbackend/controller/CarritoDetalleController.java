package pe.edu.cibertec.evertrailbackend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.evertrailbackend.dto.CarritoDetalleDTO;
import pe.edu.cibertec.evertrailbackend.entidad.CarritoDetalle;
import pe.edu.cibertec.evertrailbackend.entidad.Producto;
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

    @PostMapping("/registrar")
    public ResponseEntity<?> createCarritoDetalle(@Valid @RequestBody CarritoDetalleDTO carritoDetalleDTO) {
        try {
            CarritoDetalle carritoDetalle = mapper.map(carritoDetalleDTO, CarritoDetalle.class);
            carritoDetalleService.registrar(carritoDetalle);
            CarritoDetalleDTO nuevoCarritoDetalleDTO = mapper.map(carritoDetalle, CarritoDetalleDTO.class);
            return ResponseEntity.ok(nuevoCarritoDetalleDTO); // Devuelve el detalle del carrito creado como JSON
        } catch (Exception e) {
            e.printStackTrace(); // Imprime el stack trace para obtener más detalles del error
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
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {
        try {
            carritoDetalleService.eliminar(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace(); // Imprimir el stack trace para depuración
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el producto del carrito");
        }
    }

    @DeleteMapping("/eliminar-todos/{carritoId}")
    public ResponseEntity<?> eliminarTodosLosProductos(@PathVariable Long carritoId) {
        try {
            carritoDetalleService.eliminarTodosLosProductos(carritoId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace(); // Imprimir el stack trace para depuración
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar todos los productos del carrito");
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<?> getAllCarritoDetalles() {
        try {
            Set<CarritoDetalle> lista = carritoDetalleService.listar();
            if (lista.isEmpty()) {
                return new ResponseEntity<>(MensajeResponse.builder().mensaje("No hay registros").object(null).build(), HttpStatus.OK);
            } else {
                Set<CarritoDetalleDTO> listaDTO = lista.stream().map(m -> {
                    CarritoDetalleDTO dto = mapper.map(m, CarritoDetalleDTO.class);
                    Producto producto = m.getProducto();
                    if (producto != null) {
                        dto.setProductoNombre(producto.getNombre());
                        dto.setProductoDescripcion(producto.getDescripcion());
                    }
                    return dto;
                }).collect(Collectors.toSet());
                return new ResponseEntity<>(MensajeResponse.builder().mensaje("Existen registros").object(listaDTO).build(), HttpStatus.OK);
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
