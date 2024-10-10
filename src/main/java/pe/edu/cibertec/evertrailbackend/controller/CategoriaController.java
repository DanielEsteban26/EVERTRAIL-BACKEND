package pe.edu.cibertec.evertrailbackend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.evertrailbackend.dto.CategoriaDTO;
import pe.edu.cibertec.evertrailbackend.entidad.Categoria;
import pe.edu.cibertec.evertrailbackend.serviceImp.CategoriaService;
import pe.edu.cibertec.evertrailbackend.utils.MensajeResponse;
import pe.edu.cibertec.evertrailbackend.utils.ModeloNotFoundException;

import jakarta.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/listar")
    public ResponseEntity<?> getAllCategorias() {
        try {
            Set<Categoria> lista = categoriaService.listar();
            if (lista.isEmpty()) {
                return new ResponseEntity<>(MensajeResponse.builder().mensaje("No hay registros").object(null).build(), HttpStatus.OK);
            } else {
                Set<CategoriaDTO> listaDTO = lista.stream().map(c -> mapper.map(c, CategoriaDTO.class)).collect(Collectors.toSet());
                return new ResponseEntity<>(MensajeResponse.builder().mensaje("Existen registros").object(listaDTO).build(), HttpStatus.OK);
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> getCategoriaById(@PathVariable("id") Long id) {
        try {
            Categoria categoria = categoriaService.buscar(id);
            if (categoria == null) {
                throw new ModeloNotFoundException("ID NO ENCONTRADO : " + id);
            }
            CategoriaDTO categoriaDTO = mapper.map(categoria, CategoriaDTO.class);
            return new ResponseEntity<>(MensajeResponse.builder().mensaje("Encontrado").object(categoriaDTO).build(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> createCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO) {
        try {
            // Verificar si el nombre de la categoría ya existe
            if (categoriaService.listar().stream().anyMatch(c -> c.getNombre().equals(categoriaDTO.getNombre()))) {
                return ResponseEntity.badRequest().body(MensajeResponse.builder().mensaje("El nombre de la categoría ya existe").object(null).build());
            }

            // Registrar la nueva categoría
            Categoria categoria = mapper.map(categoriaDTO, Categoria.class);
            categoriaService.registrar(categoria);
            CategoriaDTO nuevaCategoriaDTO = mapper.map(categoria, CategoriaDTO.class);
            return new ResponseEntity<>(MensajeResponse.builder().mensaje("Categoría registrada exitosamente").object(nuevaCategoriaDTO).build(), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(MensajeResponse.builder().mensaje("Ocurrió un error al registrar la categoría").object(null).build());
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updateCategoria(@PathVariable("id") Long id, @Valid @RequestBody CategoriaDTO categoriaDTO) {
        try {
            Categoria categoria = categoriaService.buscar(id);
            if (categoria == null) {
                throw new ModeloNotFoundException("ID NO ENCONTRADO : " + id);
            }
            categoriaDTO.setId(id);
            Categoria categoriaActualizada = categoriaService.actualizar(mapper.map(categoriaDTO, Categoria.class));
            CategoriaDTO categoriaActualizadaDTO = mapper.map(categoriaActualizada, CategoriaDTO.class);
            return new ResponseEntity<>(MensajeResponse.builder().mensaje("Categoría actualizada correctamente").object(categoriaActualizadaDTO).build(), HttpStatus.OK);
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
