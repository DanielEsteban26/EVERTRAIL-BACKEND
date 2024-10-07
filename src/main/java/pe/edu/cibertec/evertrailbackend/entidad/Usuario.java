package pe.edu.cibertec.evertrailbackend.entidad;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Entidad que representa la tabla de usuarios.
 */

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único para cada usuario

    @Column(name = "nombre_usuario", unique = true ,nullable = false)
    private String nombreUsuario; // Nombre de usuario único

    @Column(name = "correo", unique = true, nullable = false)
    private String correo; // Correo electrónico único

    @Column(name = "contrasenia", nullable = false)
    private String contrasenia; // Contraseña del usuario

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Role rol; // Rol del usuario

    @OneToMany(mappedBy = "usuario")
    private Set<Pedido> pedidos; // Conjunto de pedidos realizados por el usuario

    @OneToMany(mappedBy = "usuario")
    private Set<DireccionEnvio> direccionesEnvio; // Conjunto de direcciones de envío del usuario

    @OneToMany(mappedBy = "usuario")
    private Set<MetodoPago> metodosPago; // Conjunto de métodos de pago del usuario

    @OneToMany(mappedBy = "usuario")
    private Set<ResenaProducto> reseñasProductos; // Conjunto de reseñas de productos realizadas por el usuario

    public List<String> getRoles() {
        return Collections.singletonList(rol.getNombre());
    }
}