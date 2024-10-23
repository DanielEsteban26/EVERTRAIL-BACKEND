package pe.edu.cibertec.evertrailbackend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.evertrailbackend.dto.CarritoDTO;
import pe.edu.cibertec.evertrailbackend.dto.CarritoDetalleDTO;
import pe.edu.cibertec.evertrailbackend.dto.ProductoDTO;
import pe.edu.cibertec.evertrailbackend.entidad.Carrito;
import pe.edu.cibertec.evertrailbackend.entidad.CarritoDetalle;
import pe.edu.cibertec.evertrailbackend.entidad.Producto;
import pe.edu.cibertec.evertrailbackend.serviceImp.CarritoService;
import pe.edu.cibertec.evertrailbackend.serviceImp.CarritoDetalleService;
import pe.edu.cibertec.evertrailbackend.utils.MensajeResponse;

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
    private CarritoDetalleService carritoDetalleService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/listarDetalles")
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

                    }
                    return dto;
                }).collect(Collectors.toSet());
                return new ResponseEntity<>(MensajeResponse.builder().mensaje("Existen registros").object(listaDTO).build(), HttpStatus.OK);
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping("/listar")
    public ResponseEntity<?> getAllCarritos() {
        try {
            Set<Carrito> lista = carritoService.listar();
            List<CarritoDTO> listaDTO = lista.stream().map(carrito -> {
                CarritoDTO carritoDTO = mapper.map(carrito, CarritoDTO.class);
                Set<CarritoDetalleDTO> detallesDTO = carrito.getCarritoDetalles().stream()
                        .map(detalle -> {
                            CarritoDetalleDTO detalleDTO = mapper.map(detalle, CarritoDetalleDTO.class);
                            if (detalle.getProducto() != null) {
                            }
                            return detalleDTO;
                        })
                        .collect(Collectors.toSet());
                carritoDTO.setCarritoDetalles(detallesDTO);
                return carritoDTO;
            }).collect(Collectors.toList());
            return ResponseEntity.ok(MensajeResponse.builder().mensaje("Existen registros").object(listaDTO).build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(MensajeResponse.builder().mensaje("Error interno del servidor").object(null).build());
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> createCarrito(@Valid @RequestBody CarritoDTO carritoDTO) {
        try {
            Carrito carrito = mapper.map(carritoDTO, Carrito.class);
            Carrito nuevoCarrito = carritoService.registrar(carrito);

            if (carritoDTO.getCarritoDetalles() != null) {
                Set<CarritoDetalleDTO> detalles = carritoDTO.getCarritoDetalles();
                detalles.forEach(detalleDTO -> {
                    CarritoDetalle carritoDetalle = mapper.map(detalleDTO, CarritoDetalle.class);
                    carritoDetalle.setCarrito(nuevoCarrito);
                    try {
                        carritoDetalleService.registrar(carritoDetalle);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            CarritoDTO carritoCreadoDTO = mapper.map(nuevoCarrito, CarritoDTO.class);
            return ResponseEntity.status(HttpStatus.CREATED).body(MensajeResponse.builder().mensaje("Carrito registrado exitosamente").object(carritoCreadoDTO).build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(MensajeResponse.builder().mensaje("Ocurrió un error al registrar el carrito").object(null).build());
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updateCarrito(@PathVariable("id") Long id, @Valid @RequestBody CarritoDTO carritoDTO) {
        try {
            Carrito carrito = mapper.map(carritoDTO, Carrito.class);
            carrito.setId(id);
            Carrito updatedCarrito = carritoService.actualizar(carrito);
            return ResponseEntity.ok(MensajeResponse.builder().mensaje("Carrito actualizado correctamente").object(mapper.map(updatedCarrito, CarritoDTO.class)).build());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<MensajeResponse> deleteCarrito(@PathVariable("id") Long id) {
        try {
            carritoService.eliminar(id);
            return ResponseEntity.ok(MensajeResponse.builder().mensaje("Carrito eliminado correctamente").object(null).build());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{idCarrito}")
    public ResponseEntity<?> obtenerCarritoConProductos(@PathVariable Long idCarrito) {
        return carritoService.obtenerCarritoConProductos(idCarrito)
                .map(carrito -> {
                    CarritoDTO carritoDTO = mapper.map(carrito, CarritoDTO.class);
                    Set<CarritoDetalleDTO> detallesDTO = carrito.getCarritoDetalles().stream()
                            .map(detalle -> mapper.map(detalle, CarritoDetalleDTO.class))
                            .collect(Collectors.toSet());
                    carritoDTO.setCarritoDetalles(detallesDTO);
                    return ResponseEntity.ok(carritoDTO);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{idCarrito}/detalles")
    public ResponseEntity<?> agregarProductoAlCarrito(@PathVariable Long idCarrito, @Valid @RequestBody CarritoDetalleDTO carritoDetalleDTO) {
        try {
            Carrito carrito = carritoService.buscar(idCarrito);
            if (carrito == null) {
                return ResponseEntity.notFound().build();
            }

            // Mapea el DTO a la entidad CarritoDetalle
            CarritoDetalle carritoDetalle = mapper.map(carritoDetalleDTO, CarritoDetalle.class);
            carritoDetalle.setCarrito(carrito);

            // Registra el detalle en la base de datos y obtiene el detalle guardado
            CarritoDetalle nuevoCarritoDetalle = carritoDetalleService.registrar(carritoDetalle);

            // Mapea la entidad guardada de vuelta al DTO, ahora con el ID generado
            CarritoDetalleDTO carritoDetalleCreadoDTO = mapper.map(nuevoCarritoDetalle, CarritoDetalleDTO.class);

            // Retorna el DTO con el ID actualizado
            return ResponseEntity.ok(MensajeResponse.builder().mensaje("Producto agregado al carrito").object(carritoDetalleCreadoDTO).build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(MensajeResponse.builder().mensaje("Ocurrió un error al agregar el producto al carrito").object(null).build());
        }
    }

    @PutMapping("/detalles/actualizar/{id}")
    public ResponseEntity<?> actualizarCantidad(@PathVariable("id") Long id, @Valid @RequestBody CarritoDetalleDTO carritoDetalleDTO) {
        try {
            CarritoDetalle carritoDetalle = mapper.map(carritoDetalleDTO, CarritoDetalle.class);
            carritoDetalle.setId(id);
            CarritoDetalle updatedCarritoDetalle = carritoDetalleService.actualizar(carritoDetalle);
            return ResponseEntity.ok(MensajeResponse.builder().mensaje("Cantidad actualizada correctamente").object(mapper.map(updatedCarritoDetalle, CarritoDetalleDTO.class)).build());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/detalles/eliminar/{id}")
    public ResponseEntity<MensajeResponse> eliminarProductoDelCarrito(@PathVariable("id") Long id) {
        try {
            carritoDetalleService.eliminar(id);
            return ResponseEntity.ok(MensajeResponse.builder().mensaje("Producto eliminado del carrito").object(null).build());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
