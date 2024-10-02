package pe.edu.cibertec.evertrailbackend.entidad;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Set;

/**
 * Entidad que representa la tabla de roles.
 */

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único para cada rol

    @Column(name="nombre", nullable = false)
    private String nombre; // Nombre del rol

    @OneToMany(mappedBy = "rol")
    @JsonBackReference("rol-usuario") // Rompe la recursión en la serialización
    private Set<Usuario> usuarios; // Conjunto de usuarios que tienen este rol

    // Constructor vacío
    public Role() {
    }

    // Constructor con parámetros
    public Role(Long id, String nombre, Set<Usuario> usuarios) {
        this.id = id;
        this.nombre = nombre;
        this.usuarios = usuarios;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
