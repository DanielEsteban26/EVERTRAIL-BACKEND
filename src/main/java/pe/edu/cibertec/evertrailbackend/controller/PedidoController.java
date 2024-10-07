package pe.edu.cibertec.evertrailbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.evertrailbackend.entidad.Pedido;
import pe.edu.cibertec.evertrailbackend.serviceImp.PedidoService;

import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/listar")
    public ResponseEntity<Set<Pedido>> getAllPedidos() {
        try {
            return ResponseEntity.ok(pedidoService.listar());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Pedido> getPedidoById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(pedidoService.buscar(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<Pedido> createPedido(@RequestBody Pedido pedido) {
        try {
            return ResponseEntity.ok(pedidoService.registrar(pedido));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Pedido> updatePedido(@PathVariable("id") Long id, @RequestBody Pedido pedido) {
        try {
            pedido.setId(id);
            return ResponseEntity.ok(pedidoService.actualizar(pedido));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable("id") Long id) {
        try {
            pedidoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}