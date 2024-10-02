package pe.edu.cibertec.evertrailbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.evertrailbackend.entidad.Producto;
import pe.edu.cibertec.evertrailbackend.serviceImp.ProductoService;

import java.util.Set;

@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/api/productos") // Mapea las solicitudes HTTP a /api/productos
public class ProductoController {

    @Autowired // Inyección de dependencias de Spring
    private ProductoService productoService;

    @GetMapping("/listar") // Mapea las solicitudes GET a /api/productos/listar
    public ResponseEntity<Set<Producto>> getAllProductos() {
        try {
            return ResponseEntity.ok(productoService.listar()); // Retorna todos los productos
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build(); // Retorna un error 500 si ocurre una excepción
        }
    }

    @GetMapping("/buscar/{id}") // Mapea las solicitudes GET a /api/productos/buscar/{id}
    public ResponseEntity<Producto> getProductoById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(productoService.buscar(id)); // Retorna el producto con el ID especificado
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // Retorna un error 404 si no se encuentra el producto
        }
    }

    @PostMapping("/registrar") // Mapea las solicitudes POST a /api/productos/registrar
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) {
        try {
            return ResponseEntity.ok(productoService.registrar(producto)); // Crea un nuevo producto
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build(); // Retorna un error 500 si ocurre una excepción
        }
    }

    @PutMapping("/actualizar/{id}") // Mapea las solicitudes PUT a /api/productos/actualizar/{id}
    public ResponseEntity<Producto> updateProducto(@PathVariable("id") Long id, @RequestBody Producto producto) {
        try {
            producto.setId(id); // Establece el ID del producto
            return ResponseEntity.ok(productoService.actualizar(producto)); // Actualiza el producto
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // Retorna un error 404 si no se encuentra el producto
        }
    }

    @DeleteMapping("/eliminar/{id}") // Mapea las solicitudes DELETE a /api/productos/eliminar/{id}
    public ResponseEntity<Void> deleteProducto(@PathVariable("id") Long id) {
        try {
            productoService.eliminar(id); // Elimina el producto con el ID especificado
            return ResponseEntity.noContent().build(); // Retorna un código 204 si la eliminación es exitosa
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // Retorna un error 404 si no se encuentra el producto
        }
    }
}
