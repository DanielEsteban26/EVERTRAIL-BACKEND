package pe.edu.cibertec.evertrailbackend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.evertrailbackend.dto.ImagenProductoDTO;
import pe.edu.cibertec.evertrailbackend.entidad.ImagenProducto;
import pe.edu.cibertec.evertrailbackend.serviceImp.ImagenProductoService;
import pe.edu.cibertec.evertrailbackend.utils.MensajeResponse;
import pe.edu.cibertec.evertrailbackend.utils.ModeloNotFoundException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/imagenes-producto")
public class ImagenProductoController {

    @Autowired
    private ImagenProductoService imagenProductoService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/listar")
    public ResponseEntity<?> getAllImagenesProducto() {
        try {
            List<ImagenProductoDTO> imagenesProductoDTO = imagenProductoService.listar()
                    .stream()
                    .map(imagen -> mapper.map(imagen, ImagenProductoDTO.class))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(MensajeResponse.builder().mensaje("Imágenes de producto listadas correctamente").object(imagenesProductoDTO).build(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(MensajeResponse.builder().mensaje("Error al listar las imágenes").object(null).build());
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> getImagenProductoById(@PathVariable("id") Long id) {
        try {
            ImagenProducto imagenProducto = imagenProductoService.buscar(id);
            if (imagenProducto == null) {
                throw new ModeloNotFoundException("Imagen de producto no encontrada con ID: " + id);
            }
            ImagenProductoDTO imagenProductoDTO = mapper.map(imagenProducto, ImagenProductoDTO.class);
            return new ResponseEntity<>(MensajeResponse.builder().mensaje("Imagen de producto encontrada").object(imagenProductoDTO).build(), HttpStatus.OK);
        } catch (ModeloNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MensajeResponse.builder().mensaje(e.getMessage()).object(null).build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(MensajeResponse.builder().mensaje("Error al buscar la imagen").object(null).build());
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> createImagenProducto(@RequestBody ImagenProductoDTO imagenProductoDTO) {
        try {
            // Verificar si la imagen ya existe
            if (imagenProductoService.listar().stream().anyMatch(ip -> ip.getUrlImagen().equals(imagenProductoDTO.getUrlImagen()))) {
                return ResponseEntity.badRequest().body(MensajeResponse.builder().mensaje("La imagen ya existe").object(null).build());
            }
            // Registrar la nueva imagen
            ImagenProducto imagenProducto = mapper.map(imagenProductoDTO, ImagenProducto.class);
            ImagenProducto nuevaImagen = imagenProductoService.registrar(imagenProducto);
            ImagenProductoDTO nuevaImagenDTO = mapper.map(nuevaImagen, ImagenProductoDTO.class);
            return new ResponseEntity<>(MensajeResponse.builder().mensaje("Imagen registrada exitosamente").object(nuevaImagenDTO).build(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(MensajeResponse.builder().mensaje("Error al registrar la imagen").object(null).build());
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updateImagenProducto(@PathVariable("id") Long id, @RequestBody ImagenProductoDTO imagenProductoDTO) {
        try {
            ImagenProducto imagenProducto = mapper.map(imagenProductoDTO, ImagenProducto.class);
            imagenProducto.setId(id);
            ImagenProducto imagenActualizada = imagenProductoService.actualizar(imagenProducto);
            ImagenProductoDTO imagenActualizadaDTO = mapper.map(imagenActualizada, ImagenProductoDTO.class);
            return new ResponseEntity<>(MensajeResponse.builder().mensaje("Imagen actualizada exitosamente").object(imagenActualizadaDTO).build(), HttpStatus.OK);
        } catch (ModeloNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MensajeResponse.builder().mensaje(e.getMessage()).object(null).build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(MensajeResponse.builder().mensaje("Error al actualizar la imagen").object(null).build());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> deleteImagenProducto(@PathVariable("id") Long id) {
        try {
            imagenProductoService.eliminar(id);
            return new ResponseEntity<>(MensajeResponse.builder().mensaje("Imagen eliminada exitosamente").object(null).build(), HttpStatus.NO_CONTENT);
        } catch (ModeloNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MensajeResponse.builder().mensaje(e.getMessage()).object(null).build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(MensajeResponse.builder().mensaje("Error al eliminar la imagen").object(null).build());
        }
    }
}
