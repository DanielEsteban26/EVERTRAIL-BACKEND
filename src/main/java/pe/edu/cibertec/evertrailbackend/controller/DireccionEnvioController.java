package pe.edu.cibertec.evertrailbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.evertrailbackend.entidad.DireccionEnvio;
import pe.edu.cibertec.evertrailbackend.serviceImp.DireccionEnvioService;

import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/direcciones-envio")
public class DireccionEnvioController {

    @Autowired
    private DireccionEnvioService direccionEnvioService;

    @GetMapping("/listar")
    public ResponseEntity<Set<DireccionEnvio>> getAllDireccionesEnvio() {
        try {
            return ResponseEntity.ok(direccionEnvioService.listar());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<DireccionEnvio> getDireccionEnvioById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(direccionEnvioService.buscar(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<DireccionEnvio> createDireccionEnvio(@RequestBody DireccionEnvio direccionEnvio) {
        try {
            return ResponseEntity.ok(direccionEnvioService.registrar(direccionEnvio));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<DireccionEnvio> updateDireccionEnvio(@PathVariable("id") Long id, @RequestBody DireccionEnvio direccionEnvio) {
        try {
            direccionEnvio.setId(id);
            return ResponseEntity.ok(direccionEnvioService.actualizar(direccionEnvio));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> deleteDireccionEnvio(@PathVariable("id") Long id) {
        try {
            direccionEnvioService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}