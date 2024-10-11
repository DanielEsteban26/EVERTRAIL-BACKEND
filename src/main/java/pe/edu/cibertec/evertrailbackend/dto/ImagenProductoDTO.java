package pe.edu.cibertec.evertrailbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ImagenProductoDTO {

    private Long id;

    @NotNull(message = "El ID del producto no puede ser nulo")
    private Long productoId;

    @NotBlank(message = "La URL de la imagen no puede estar vacía")
    private String urlImagen;
}
