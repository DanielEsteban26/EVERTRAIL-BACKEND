package pe.edu.cibertec.evertrailbackend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.evertrailbackend.dto.PedidoDTO;
import pe.edu.cibertec.evertrailbackend.entidad.Pedido;
import pe.edu.cibertec.evertrailbackend.serviceImp.PedidoService;
import pe.edu.cibertec.evertrailbackend.utils.MensajeResponse;
import pe.edu.cibertec.evertrailbackend.utils.ModeloNotFoundException;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/listar")
    public ResponseEntity<?> getAllPedidos() {
        try {
            Set<Pedido> lista = pedidoService.listar();
            if (lista.isEmpty()) {
                return new ResponseEntity<>(MensajeResponse.builder().mensaje("No hay registros").object(null).build(), HttpStatus.OK);
            } else {
                List<PedidoDTO> listaDTO = lista.stream()
                        .map(m -> mapper.map(m, PedidoDTO.class))
                        .collect(Collectors.toList());
                return new ResponseEntity<>(MensajeResponse.builder().mensaje("Existen registros").object(listaDTO).build(), HttpStatus.OK);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MensajeResponse.builder().mensaje("Error al listar los pedidos").object(null).build());
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> getPedidoById(@PathVariable("id") Long id) {
        try {
            Pedido pedido = pedidoService.buscar(id);
            if (pedido == null) {
                throw new ModeloNotFoundException("ID NO ENCONTRADO : " + id);
            }
            PedidoDTO pedidoDTO = mapper.map(pedido, PedidoDTO.class);
            return new ResponseEntity<>(MensajeResponse.builder().mensaje("Pedido encontrado").object(pedidoDTO).build(), HttpStatus.OK);
        } catch (ModeloNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MensajeResponse.builder().mensaje(e.getMessage()).object(null).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MensajeResponse.builder().mensaje("Error al buscar el pedido").object(null).build());
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> createPedido(@Valid @RequestBody PedidoDTO pedidoDTO) {
        try {
            Pedido pedido = mapper.map(pedidoDTO, Pedido.class);
            Pedido pedidoGuardado = pedidoService.registrar(pedido);
            PedidoDTO pedidoGuardadoDTO = mapper.map(pedidoGuardado, PedidoDTO.class);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Pedido registrado exitosamente")
                    .object(pedidoGuardadoDTO)
                    .build(), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MensajeResponse.builder().mensaje("Error al registrar el pedido").object(null).build());
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updatePedido(@PathVariable("id") Long id, @Valid @RequestBody PedidoDTO pedidoDTO) {
        try {
            Pedido pedido = mapper.map(pedidoDTO, Pedido.class);
            pedido.setId(id);
            Pedido updatedPedido = pedidoService.actualizar(pedido);
            PedidoDTO pedidoActualizadoDTO = mapper.map(updatedPedido, PedidoDTO.class);
            return new ResponseEntity<>(MensajeResponse.builder().mensaje("Pedido actualizado exitosamente").object(pedidoActualizadoDTO).build(), HttpStatus.OK);
        } catch (ModeloNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MensajeResponse.builder().mensaje(e.getMessage()).object(null).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MensajeResponse.builder().mensaje("Error al actualizar el pedido").object(null).build());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> deletePedido(@PathVariable("id") Long id) {
        try {
            pedidoService.eliminar(id);
            return new ResponseEntity<>(MensajeResponse.builder().mensaje("Pedido eliminado exitosamente").object(null).build(), HttpStatus.NO_CONTENT);
        } catch (ModeloNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MensajeResponse.builder().mensaje(e.getMessage()).object(null).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MensajeResponse.builder().mensaje("Error al eliminar el pedido").object(null).build());
        }
    }
}
