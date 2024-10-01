package pe.edu.cibertec.evertrailbackend.entidad;

import jakarta.persistence.*;

import java.util.Set;

/**
 * Entidad que representa la tabla de categorías.
 */

@Entity
@Table(name = "categorias")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único para cada categoría

    @Column(name = "nombre", unique = true, nullable = false)
    private String nombre; // Nombre de la categoría, único y no nulo

    @Column(name = "descripcion")
    private String descripcion; // Descripción de la categoría

    @OneToMany(mappedBy = "categoria")
    private Set<Producto> productos; // Conjunto de productos que pertenecen a esta categoría

    // Constructor vacío
    public Categoria() {
    }
    // Constructor con parámetros
    public Categoria(Long id, String nombre, String descripcion, Set<Producto> productos) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.productos = productos;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }
}
