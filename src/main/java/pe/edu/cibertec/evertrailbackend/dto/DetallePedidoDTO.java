package pe.edu.cibertec.evertrailbackend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class DetallePedidoDTO {

    private Long id;

    @NotNull(message = "El ID del pedido no puede ser nulo")
    private Long pedidoId;

    @NotNull(message = "El ID del producto no puede ser nulo")
    private Long productoId;

    @NotNull(message = "La cantidad no puede ser nula")
    @Positive(message = "La cantidad debe ser un número positivo")
    private Integer cantidad;

    @NotNull(message = "El precio no puede ser nulo")
    @Positive(message = "El precio debe ser un número positivo")
    private Double precio;
}
