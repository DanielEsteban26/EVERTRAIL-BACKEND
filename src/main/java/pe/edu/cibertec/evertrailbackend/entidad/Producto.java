package pe.edu.cibertec.evertrailbackend.entidad;

import jakarta.persistence.*;

import java.util.Set;

/**
 * Entidad que representa la tabla de productos.
 */

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único para cada producto

    @Column(name = "nombre", nullable = false)
    private String nombre; // Nombre del producto, no nulo

    @Column(name = "descripcion")
    private String descripcion; // Descripción del producto

    @Column(name = "precio", nullable = false)
    private Double precio; // Precio del producto, no nulo

    @Column(name = "stock", nullable = false)
    private Integer stock; // Cantidad de stock del producto, no nulo

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria; // Categoría a la que pertenece el producto

    @OneToMany(mappedBy = "producto")
    private Set<ImagenProducto> imagenesProductos; // Conjunto de imágenes del producto

    @OneToMany(mappedBy = "producto")
    private Set<DetallePedido> detallesPedido; // Conjunto de detalles de pedidos que incluyen este producto

    @OneToMany(mappedBy = "producto")
    private Set<ReseñaProducto> reseñasProductos; // Conjunto de reseñas del producto

    // Constructor vacío
    public Producto() {
    }

    // Constructor con parámetros
    public Producto(Long id, String nombre, String descripcion, Double precio, Integer stock, Categoria categoria, Set<ImagenProducto> imagenesProductos, Set<DetallePedido> detallesPedido, Set<ReseñaProducto> reseñasProductos) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
        this.imagenesProductos = imagenesProductos;
        this.detallesPedido = detallesPedido;
        this.reseñasProductos = reseñasProductos;
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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Set<ImagenProducto> getImagenesProductos() {
        return imagenesProductos;
    }

    public void setImagenesProductos(Set<ImagenProducto> imagenesProductos) {
        this.imagenesProductos = imagenesProductos;
    }

    public Set<DetallePedido> getDetallesPedido() {
        return detallesPedido;
    }

    public void setDetallesPedido(Set<DetallePedido> detallesPedido) {
        this.detallesPedido = detallesPedido;
    }

    public Set<ReseñaProducto> getReseñasProductos() {
        return reseñasProductos;
    }

    public void setReseñasProductos(Set<ReseñaProducto> reseñasProductos) {
        this.reseñasProductos = reseñasProductos;
    }
}
