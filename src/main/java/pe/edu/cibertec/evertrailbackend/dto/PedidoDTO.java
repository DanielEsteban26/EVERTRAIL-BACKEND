package pe.edu.cibertec.evertrailbackend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PedidoDTO {

    private Long id;

    @NotNull(message = "El ID del usuario no puede ser nulo")
    private Long usuarioId;

    @NotNull(message = "El precio total no puede ser nulo")
    @Positive(message = "El precio total debe ser un número positivo")
    private Double precioTotal;

    private String estado = "Pendiente"; // Valor predeterminado
}
