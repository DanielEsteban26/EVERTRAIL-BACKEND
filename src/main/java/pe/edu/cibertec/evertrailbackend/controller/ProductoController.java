package pe.edu.cibertec.evertrailbackend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.evertrailbackend.dto.ProductoDTO;
import pe.edu.cibertec.evertrailbackend.entidad.Producto;
import pe.edu.cibertec.evertrailbackend.serviceImp.ProductoService;
import pe.edu.cibertec.evertrailbackend.utils.MensajeResponse;
import pe.edu.cibertec.evertrailbackend.utils.ModeloNotFoundException;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/listar")
    public ResponseEntity<?> getAllProductos() {
        try {
            Set<Producto> lista = productoService.listar();
            if (lista.isEmpty()) {
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("No hay registros")
                        .object(null)
                        .build(), HttpStatus.OK);
            } else {
                List<ProductoDTO> listaDTO = lista.stream()
                        .map(m -> mapper.map(m, ProductoDTO.class))
                        .collect(Collectors.toList());
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("Existen registros")
                        .object(listaDTO)
                        .build(), HttpStatus.OK);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MensajeResponse.builder()
                            .mensaje("Error al listar productos")
                            .object(null)
                            .build());
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> getProductoById(@PathVariable("id") Long id) {
        try {
            Producto producto = productoService.buscar(id);
            if (producto == null) {
                throw new ModeloNotFoundException("ID NO ENCONTRADO : " + id);
            }
            ProductoDTO productoDTO = mapper.map(producto, ProductoDTO.class);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Producto encontrado")
                    .object(productoDTO)
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
                            .mensaje("Error al buscar el producto")
                            .object(null)
                            .build());
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> createProducto(@Valid @RequestBody ProductoDTO productoDTO) {
        try {
            Producto producto = mapper.map(productoDTO, Producto.class);
            Producto productoGuardado = productoService.registrar(producto);
            ProductoDTO productoGuardadoDTO = mapper.map(productoGuardado, ProductoDTO.class);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Producto registrado exitosamente")
                    .object(productoGuardadoDTO)
                    .build(), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MensajeResponse.builder()
                            .mensaje("Error al registrar el producto")
                            .object(null)
                            .build());
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updateProducto(@PathVariable("id") Long id, @Valid @RequestBody ProductoDTO productoDTO) {
        try {
            Producto producto = mapper.map(productoDTO, Producto.class);
            producto.setId(id);
            Producto updatedProducto = productoService.actualizar(producto);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Producto actualizado correctamente")
                    .object(mapper.map(updatedProducto, ProductoDTO.class))
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
                            .mensaje("Error al actualizar el producto")
                            .object(null)
                            .build());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> deleteProducto(@PathVariable("id") Long id) {
        try {
            productoService.eliminar(id);
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
                            .mensaje("Error al eliminar el producto")
                            .object(null)
                            .build());
        }
    }
}
