package pe.edu.cibertec.evertrailbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.evertrailbackend.entidad.ImagenProducto;
import pe.edu.cibertec.evertrailbackend.serviceImp.ImagenProductoService;

import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/imagenes-producto")
public class ImagenProductoController {

    @Autowired
    private ImagenProductoService imagenProductoService;

    @GetMapping("/listar")
    public ResponseEntity<Set<ImagenProducto>> getAllImagenesProducto() {
        try {
            return ResponseEntity.ok(imagenProductoService.listar());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ImagenProducto> getImagenProductoById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(imagenProductoService.buscar(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<String> createImagenProducto(@RequestBody ImagenProducto imagenProducto) {
        try {
            // Verificar si la imagen ya existe
            if (imagenProductoService.listar().stream().anyMatch(ip -> ip.getUrlImagen().equals(imagenProducto.getUrlImagen()))) {
                return ResponseEntity.badRequest().body("La imagen ya existe.");
            }
            // Registrar la nueva imagen
            imagenProductoService.registrar(imagenProducto);
            return ResponseEntity.ok("Imagen registrada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Ocurrió un error al registrar la imagen.");
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ImagenProducto> updateImagenProducto(@PathVariable("id") Long id, @RequestBody ImagenProducto imagenProducto) {
        try {
            imagenProducto.setId(id);
            return ResponseEntity.ok(imagenProductoService.actualizar(imagenProducto));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> deleteImagenProducto(@PathVariable("id") Long id) {
        try {
            imagenProductoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}