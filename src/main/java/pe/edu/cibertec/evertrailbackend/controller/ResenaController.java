package pe.edu.cibertec.evertrailbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.evertrailbackend.entidad.ResenaProducto;
import pe.edu.cibertec.evertrailbackend.serviceImp.ResenaProductoService;

import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/resenas")
public class ResenaController {

    @Autowired
    private ResenaProductoService resenaService;

    @GetMapping("/listar")
    public ResponseEntity<Set<ResenaProducto>> getAllResenas() {
        try {
            return ResponseEntity.ok(resenaService.listar());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ResenaProducto> getResenaById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(resenaService.buscar(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<ResenaProducto> createResena(@RequestBody ResenaProducto resena) {
        try {
            return ResponseEntity.ok(resenaService.registrar(resena));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ResenaProducto> updateResena(@PathVariable("id") Long id, @RequestBody ResenaProducto resena) {
        try {
            resena.setId(id);
            return ResponseEntity.ok(resenaService.actualizar(resena));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> deleteResena(@PathVariable("id") Long id) {
        try {
            resenaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
