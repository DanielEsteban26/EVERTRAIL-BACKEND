package pe.edu.cibertec.evertrailbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.evertrailbackend.entidad.Categoria;
import pe.edu.cibertec.evertrailbackend.serviceImp.CategoriaService;

import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/listar")
    public ResponseEntity<Set<Categoria>> getAllCategorias() {
        try {
            return ResponseEntity.ok(categoriaService.listar());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Categoria> getCategoriaById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(categoriaService.buscar(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<String> createCategoria(@RequestBody Categoria categoria) {
        try {
            // Verificar si el nombre de la categoría ya existe
            if (categoriaService.listar().stream().anyMatch(c -> c.getNombre().equals(categoria.getNombre()))) {
                return ResponseEntity.badRequest().body("El nombre de la categoría ya existe.");
            }

            // Registrar la nueva categoría
            categoriaService.registrar(categoria);
            return ResponseEntity.ok("Categoría registrada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Ocurrió un error al registrar la categoría.");
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Categoria> updateCategoria(@PathVariable("id") Long id, @RequestBody Categoria categoria) {
        try {
            categoria.setId(id);
            return ResponseEntity.ok(categoriaService.actualizar(categoria));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable("id") Long id) {
        try {
            categoriaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}