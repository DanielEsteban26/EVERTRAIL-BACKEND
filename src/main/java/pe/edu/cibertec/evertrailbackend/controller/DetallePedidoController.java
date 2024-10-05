package pe.edu.cibertec.evertrailbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.evertrailbackend.entidad.DetallePedido;
import pe.edu.cibertec.evertrailbackend.serviceImp.DetallePedidoService;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200") // Permite solicitudes desde http://localhost:4200
@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/api/detalles-pedido") // Mapea las solicitudes HTTP a /api/detalles-pedido
public class DetallePedidoController {

    @Autowired // Inyección de dependencias de Spring
    private DetallePedidoService detallePedidoService;

    @GetMapping("/listar") // Mapea las solicitudes GET a /api/detalles-pedido/listar
    public ResponseEntity<Set<DetallePedido>> getAllDetallesPedido() {
        try {
            return ResponseEntity.ok(detallePedidoService.listar()); // Retorna todos los detalles de pedido
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build(); // Retorna un error 500 si ocurre una excepción
        }
    }

    @GetMapping("/buscar/{id}") // Mapea las solicitudes GET a /api/detalles-pedido/buscar/{id}
    public ResponseEntity<DetallePedido> getDetallePedidoById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(detallePedidoService.buscar(id)); // Retorna el detalle de pedido con el ID especificado
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // Retorna un error 404 si no se encuentra el detalle de pedido
        }
    }

    @PostMapping("/registrar") // Mapea las solicitudes POST a /api/detalles-pedido/registrar
    public ResponseEntity<DetallePedido> createDetallePedido(@RequestBody DetallePedido detallePedido) {
        try {
            return ResponseEntity.ok(detallePedidoService.registrar(detallePedido)); // Crea un nuevo detalle de pedido
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build(); // Retorna un error 500 si ocurre una excepción
        }
    }

    @PutMapping("/actualizar/{id}") // Mapea las solicitudes PUT a /api/detalles-pedido/actualizar/{id}
    public ResponseEntity<DetallePedido> updateDetallePedido(@PathVariable("id") Long id, @RequestBody DetallePedido detallePedido) {
        try {
            detallePedido.setId(id); // Establece el ID del detalle de pedido
            return ResponseEntity.ok(detallePedidoService.actualizar(detallePedido)); // Actualiza el detalle de pedido
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // Retorna un error 404 si no se encuentra el detalle de pedido
        }
    }

    @DeleteMapping("/eliminar/{id}") // Mapea las solicitudes DELETE a /api/detalles-pedido/eliminar/{id}
    public ResponseEntity<Void> deleteDetallePedido(@PathVariable("id") Long id) {
        try {
            detallePedidoService.eliminar(id); // Elimina el detalle de pedido con el ID especificado
            return ResponseEntity.noContent().build(); // Retorna un código 204 si la eliminación es exitosa
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // Retorna un error 404 si no se encuentra el detalle de pedido
        }
    }
}
