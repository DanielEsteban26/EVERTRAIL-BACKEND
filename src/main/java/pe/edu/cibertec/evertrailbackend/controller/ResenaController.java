package pe.edu.cibertec.evertrailbackend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.evertrailbackend.dto.ResenaProductoDTO;
import pe.edu.cibertec.evertrailbackend.entidad.ResenaProducto;
import pe.edu.cibertec.evertrailbackend.serviceImp.ResenaProductoService;
import pe.edu.cibertec.evertrailbackend.utils.MensajeResponse;
import pe.edu.cibertec.evertrailbackend.utils.ModeloNotFoundException;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/resenas")
public class ResenaController {

    @Autowired
    private ResenaProductoService resenaService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/listar")
    public ResponseEntity<?> getAllResenas() {
        try {
            Set<ResenaProducto> lista = resenaService.listar();
            if (lista.isEmpty()) {
                return ResponseEntity.ok(MensajeResponse.builder()
                        .mensaje("No hay reseñas registradas")
                        .object(null)
                        .build());
            }
            List<ResenaProductoDTO> listaDTO = lista.stream()
                    .map(m -> mapper.map(m, ResenaProductoDTO.class))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(MensajeResponse.builder()
                    .mensaje("Reseñas encontradas")
                    .object(listaDTO)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MensajeResponse.builder()
                            .mensaje("Error al listar las reseñas")
                            .object(null)
                            .build());
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> getResenaById(@PathVariable("id") Long id) {
        try {
            ResenaProducto resena = resenaService.buscar(id);
            if (resena == null) {
                throw new ModeloNotFoundException("ID NO ENCONTRADO : " + id);
            }
            ResenaProductoDTO resenaDTO = mapper.map(resena, ResenaProductoDTO.class);
            return ResponseEntity.ok(MensajeResponse.builder()
                    .mensaje("Reseña encontrada")
                    .object(resenaDTO)
                    .build());
        } catch (ModeloNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MensajeResponse.builder()
                            .mensaje(e.getMessage())
                            .object(null)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MensajeResponse.builder()
                            .mensaje("Error al buscar la reseña")
                            .object(null)
                            .build());
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> createResena(@Valid @RequestBody ResenaProductoDTO resenaDTO) {
        try {
            ResenaProducto resena = mapper.map(resenaDTO, ResenaProducto.class);
            ResenaProducto resenaGuardada = resenaService.registrar(resena);
            ResenaProductoDTO resenaGuardadaDTO = mapper.map(resenaGuardada, ResenaProductoDTO.class);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(MensajeResponse.builder()
                            .mensaje("Reseña registrada exitosamente")
                            .object(resenaGuardadaDTO)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MensajeResponse.builder()
                            .mensaje("Error al registrar la reseña")
                            .object(null)
                            .build());
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updateResena(@PathVariable("id") Long id, @Valid @RequestBody ResenaProductoDTO resenaDTO) {
        try {
            ResenaProducto resena = mapper.map(resenaDTO, ResenaProducto.class);
            resena.setId(id);
            ResenaProducto updatedResena = resenaService.actualizar(resena);
            ResenaProductoDTO updatedResenaDTO = mapper.map(updatedResena, ResenaProductoDTO.class);
            return ResponseEntity.ok(MensajeResponse.builder()
                    .mensaje("Reseña actualizada correctamente")
                    .object(updatedResenaDTO)
                    .build());
        } catch (ModeloNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MensajeResponse.builder()
                            .mensaje(e.getMessage())
                            .object(null)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MensajeResponse.builder()
                            .mensaje("Error al actualizar la reseña")
                            .object(null)
                            .build());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> deleteResena(@PathVariable("id") Long id) {
        try {
            resenaService.eliminar(id);
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
                            .mensaje("Error al eliminar la reseña")
                            .object(null)
                            .build());
        }
    }
}
