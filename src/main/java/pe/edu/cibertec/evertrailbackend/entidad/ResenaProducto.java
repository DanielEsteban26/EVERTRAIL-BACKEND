package pe.edu.cibertec.evertrailbackend.entidad;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

/**
 * Entidad que representa la tabla de reseñas de productos.
 */

@Entity
@Table(name = "reseñas_productos")
public class ResenaProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único para cada reseña de producto

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    @JsonBackReference("producto-reseña") // Rompe la recursión en la serialización
    private Producto producto; // Producto al que pertenece la reseña

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonBackReference("usuario-reseñaProducto") // Breaks the recursion in serialization
    private Usuario usuario; // Usuario que realizó la reseña

    @Column(name = "calificacion", nullable = false)
    private Integer calificacion; // Calificación del producto

    @Column(name = "reseña")
    private String reseña; // Texto de la reseña

    // Constructor vacío
    public ResenaProducto() {
    }

    // Constructor con parámetros
    public ResenaProducto(Long id, String reseña, Integer calificacion, Usuario usuario, Producto producto) {
        this.id = id;
        this.reseña = reseña;
        this.calificacion = calificacion;
        this.usuario = usuario;
        this.producto = producto;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReseña() {
        return reseña;
    }

    public void setReseña(String reseña) {
        this.reseña = reseña;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }
}
