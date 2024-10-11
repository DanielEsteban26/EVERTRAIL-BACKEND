package pe.edu.cibertec.evertrailbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RoleDTO {

    private Long id;

    @NotBlank(message = "El nombre del rol no puede estar vacío")
    @Size(max = 50, message = "El nombre del rol no puede exceder los 50 caracteres")
    private String nombre;

}
