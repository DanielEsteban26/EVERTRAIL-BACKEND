package pe.edu.cibertec.evertrailbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ResenaProductoDTO {

    private Long id;

    @NotNull(message = "El ID del producto no puede ser nulo")
    private Long productoId;

    @NotNull(message = "El ID del usuario no puede ser nulo")
    private Long usuarioId;

    @NotNull(message = "La calificación no puede ser nula")
    @Positive(message = "La calificación debe ser un número positivo")
    private Integer calificacion;

    @NotBlank(message = "La reseña no puede estar vacía")
    private String resena;
}
