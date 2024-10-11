package pe.edu.cibertec.evertrailbackend.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class UsuarioDTO {

    private Long id;

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Size(max = 50, message = "El nombre de usuario no puede exceder los 50 caracteres")
    private String nombreUsuario;

    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "El correo debe ser válido")
    private String correo;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String contrasenia;

    @NotNull
    private RoleDTO rol; // Suponiendo que tienes un DTO para Rol

}
