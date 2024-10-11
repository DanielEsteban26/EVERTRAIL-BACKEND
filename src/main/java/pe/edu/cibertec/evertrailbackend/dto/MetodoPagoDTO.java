package pe.edu.cibertec.evertrailbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MetodoPagoDTO {

    private Long id;

    @NotNull(message = "El ID del usuario no puede ser nulo")
    private Long usuarioId;

    @NotBlank(message = "El número de tarjeta no puede estar vacío")
    private String numeroTarjeta;

    @NotBlank(message = "El nombre del titular no puede estar vacío")
    private String nombreTitular;

    @NotBlank(message = "La fecha de expiración no puede estar vacía")
    private String fechaExpiracion;

    @NotBlank(message = "El CVV no puede estar vacío")
    private String cvv;
}
