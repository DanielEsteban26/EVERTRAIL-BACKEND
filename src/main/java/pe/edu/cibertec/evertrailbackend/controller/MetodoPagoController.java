package pe.edu.cibertec.evertrailbackend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.evertrailbackend.entidad.MetodoPago;
import pe.edu.cibertec.evertrailbackend.serviceImp.MetodoPagoService;

import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/metodos-pago")
public class MetodoPagoController {

    @Autowired
    private MetodoPagoService metodoPagoService;

    @GetMapping("/listar")
    public ResponseEntity<Set<MetodoPago>> getAllMetodosPago() {
        try {
            return ResponseEntity.ok(metodoPagoService.listar());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<MetodoPago> getMetodoPagoById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(metodoPagoService.buscar(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<MetodoPago> createMetodoPago(@RequestBody MetodoPago metodoPago) {
        try {
            return ResponseEntity.ok(metodoPagoService.registrar(metodoPago));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<MetodoPago> updateMetodoPago(@PathVariable("id") Long id, @RequestBody MetodoPago metodoPago) {
        try {
            metodoPago.setId(id);
            return ResponseEntity.ok(metodoPagoService.actualizar(metodoPago));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> deleteMetodoPago(@PathVariable("id") Long id) {
        try {
            metodoPagoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}