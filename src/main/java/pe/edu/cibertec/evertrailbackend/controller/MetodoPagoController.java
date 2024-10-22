package pe.edu.cibertec.evertrailbackend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.evertrailbackend.dto.MetodoPagoDTO;
import pe.edu.cibertec.evertrailbackend.entidad.MetodoPago;
import pe.edu.cibertec.evertrailbackend.serviceImp.MetodoPagoService;
import pe.edu.cibertec.evertrailbackend.utils.MensajeResponse;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/metodos-pago")
public class MetodoPagoController {

    @Autowired
    private MetodoPagoService metodoPagoService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/listar")
    public ResponseEntity<?> getAllMetodosPago() {
        try {
            Set<MetodoPago> lista = metodoPagoService.listar();
            if (lista.isEmpty()) {
                return new ResponseEntity<>(MensajeResponse.builder().mensaje("No hay registros").object(null).build(), HttpStatus.OK);
            } else {
                List<MetodoPagoDTO> listaDTO = lista.stream()
                        .map(m -> mapper.map(m, MetodoPagoDTO.class))
                        .collect(Collectors.toList());
                return new ResponseEntity<>(MensajeResponse.builder().mensaje("Existen registros").object(listaDTO).build(), HttpStatus.OK);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MensajeResponse.builder().mensaje("Error al listar los métodos de pago").object(null).build());
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarMetodoPago(@RequestBody MetodoPagoDTO metodoPagoDTO) {
        try {
            MetodoPago metodoPago = mapper.map(metodoPagoDTO, MetodoPago.class);
            metodoPagoService.registrar(metodoPago);
            MetodoPagoDTO nuevoMetodoPagoDTO = mapper.map(metodoPago, MetodoPagoDTO.class);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Método de pago registrado exitosamente")
                    .object(nuevoMetodoPagoDTO)
                    .build(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MensajeResponse.builder().mensaje("Error al registrar el método de pago").object(null).build());
        }
    }

    @PostMapping("/procesar")
    public ResponseEntity<?> procesarPago(@RequestBody MetodoPagoDTO metodoPagoDTO) {
        try {
            boolean pagoProcesado = metodoPagoService.procesarPago(metodoPagoDTO);
            if (pagoProcesado) {
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("Pago procesado exitosamente")
                        .object(null)
                        .build(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("Error al procesar el pago")
                        .object(null)
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MensajeResponse.builder().mensaje("Error al procesar el pago").object(null).build());
        }
    }
}