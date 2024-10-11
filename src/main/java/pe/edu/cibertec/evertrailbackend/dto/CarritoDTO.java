package pe.edu.cibertec.evertrailbackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class CarritoDTO {

    private Long id;

    @NotNull(message = "El ID del usuario no puede ser nulo")
    private Long usuarioId;

    private Set<CarritoDetalleDTO> carritoDetalles; // Detalles del carrito
}
