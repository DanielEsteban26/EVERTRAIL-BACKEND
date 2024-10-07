package pe.edu.cibertec.evertrailbackend.entidad;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Entidad que representa la tabla de roles.
 */

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único para cada rol

    @Column(name="nombre", nullable = false)
    private String nombre; // Nombre del rol

    @OneToMany(mappedBy = "rol")
    private Set<Usuario> usuarios; // Conjunto de usuarios que tienen este rol
}