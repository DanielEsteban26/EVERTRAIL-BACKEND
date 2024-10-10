package pe.edu.cibertec.evertrailbackend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.evertrailbackend.dto.DetallePedidoDTO;
import pe.edu.cibertec.evertrailbackend.entidad.DetallePedido;
import pe.edu.cibertec.evertrailbackend.serviceImp.DetallePedidoService;
import pe.edu.cibertec.evertrailbackend.utils.MensajeResponse;
import pe.edu.cibertec.evertrailbackend.utils.ModeloNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/detalles-pedido")
public class DetallePedidoController {

    @Autowired
    private DetallePedidoService detallePedidoService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/listar")
    public ResponseEntity<?> getAllDetallesPedido() {
        try {
            List<DetallePedidoDTO> detallesPedidoDTO = detallePedidoService.listar()
                    .stream()
                    .map(detalle -> mapper.map(detalle, DetallePedidoDTO.class))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(MensajeResponse.builder().mensaje("Detalles de pedido listados correctamente").object(detallesPedidoDTO).build(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(MensajeResponse.builder().mensaje("Error al listar los detalles de pedido").object(null).build());
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> getDetallePedidoById(@PathVariable("id") Long id) {
        try {
            DetallePedido detallePedido = detallePedidoService.buscar(id);
            if (detallePedido == null) {
                throw new ModeloNotFoundException("Detalle de pedido no encontrado con ID: " + id);
            }
            DetallePedidoDTO detallePedidoDTO = mapper.map(detallePedido, DetallePedidoDTO.class);
            return new ResponseEntity<>(MensajeResponse.builder().mensaje("Detalle de pedido encontrado").object(detallePedidoDTO).build(), HttpStatus.OK);
        } catch (ModeloNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MensajeResponse.builder().mensaje(e.getMessage()).object(null).build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(MensajeResponse.builder().mensaje("Error al buscar el detalle de pedido").object(null).build());
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> createDetallePedido(@RequestBody DetallePedidoDTO detallePedidoDTO) {
        try {
            DetallePedido detallePedido = mapper.map(detallePedidoDTO, DetallePedido.class);
            DetallePedido detalleRegistrado = detallePedidoService.registrar(detallePedido);
            DetallePedidoDTO detalleRegistradoDTO = mapper.map(detalleRegistrado, DetallePedidoDTO.class);
            return new ResponseEntity<>(MensajeResponse.builder().mensaje("Detalle de pedido registrado exitosamente").object(detalleRegistradoDTO).build(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(MensajeResponse.builder().mensaje("Error al registrar el detalle de pedido").object(null).build());
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updateDetallePedido(@PathVariable("id") Long id, @RequestBody DetallePedidoDTO detallePedidoDTO) {
        try {
            DetallePedido detallePedido = mapper.map(detallePedidoDTO, DetallePedido.class);
            detallePedido.setId(id);
            DetallePedido detalleActualizado = detallePedidoService.actualizar(detallePedido);
            DetallePedidoDTO detalleActualizadoDTO = mapper.map(detalleActualizado, DetallePedidoDTO.class);
            return new ResponseEntity<>(MensajeResponse.builder().mensaje("Detalle de pedido actualizado exitosamente").object(detalleActualizadoDTO).build(), HttpStatus.OK);
        } catch (ModeloNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MensajeResponse.builder().mensaje(e.getMessage()).object(null).build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(MensajeResponse.builder().mensaje("Error al actualizar el detalle de pedido").object(null).build());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> deleteDetallePedido(@PathVariable("id") Long id) {
        try {
            detallePedidoService.eliminar(id);
            return new ResponseEntity<>(MensajeResponse.builder().mensaje("Detalle de pedido eliminado exitosamente").object(null).build(), HttpStatus.NO_CONTENT);
        } catch (ModeloNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MensajeResponse.builder().mensaje(e.getMessage()).object(null).build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(MensajeResponse.builder().mensaje("Error al eliminar el detalle de pedido").object(null).build());
        }
    }
}
