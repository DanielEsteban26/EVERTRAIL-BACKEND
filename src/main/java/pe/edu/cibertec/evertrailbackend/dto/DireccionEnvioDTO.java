package pe.edu.cibertec.evertrailbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DireccionEnvioDTO {

    private Long id;

    @NotNull(message = "El ID del usuario no puede ser nulo")
    private Long usuarioId;

    @NotBlank(message = "La línea de dirección 1 no puede estar vacía")
    private String lineaDireccion1;

    private String lineaDireccion2;

    @NotBlank(message = "La ciudad no puede estar vacía")
    private String ciudad;

    @NotBlank(message = "El estado no puede estar vacío")
    private String estado;

    @NotBlank(message = "El código postal no puede estar vacío")
    private String codigoPostal;

    @NotBlank(message = "El país no puede estar vacío")
    private String pais;
}
