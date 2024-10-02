package pe.edu.cibertec.evertrailbackend.entidad;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

/**
 * Entidad que representa la tabla de imágenes de productos.
 */

@Entity
@Table(name = "imagenes_productos")
public class ImagenProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único para cada imagen de producto

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    @JsonBackReference("producto-imagen") // Rompe la recursión en la serialización
    private Producto producto; // Producto al que pertenece la imagen

    @Column(name = "url_imagen", nullable = false)
    private String urlImagen; // URL de la imagen del producto

    // Constructor vacío
    public ImagenProducto() {
    }

    // Constructor con parámetros
    public ImagenProducto(Long id, Producto producto, String urlImagen) {
        this.id = id;
        this.producto = producto;
        this.urlImagen = urlImagen;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }


}
