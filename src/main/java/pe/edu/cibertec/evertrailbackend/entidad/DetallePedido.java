package pe.edu.cibertec.evertrailbackend.entidad;

import jakarta.persistence.*;

/**
 * Entidad que representa la tabla de detalles del pedido.
 */

@Entity
@Table(name = "detalles_pedido")
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único para cada detalle del pedido

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido; // Pedido al que pertenece el detalle

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto; // Producto al que pertenece el detalle

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad; // Cantidad del producto en el pedido

    @Column(name = "precio", nullable = false)
    private Double precio; // Precio del producto en el pedido

    // Constructor vacío
    public DetallePedido() {
    }

    // Constructor con parámetros
    public DetallePedido(Long id, Double precio, Integer cantidad, Producto producto, Pedido pedido) {
        this.id = id;
        this.precio = precio;
        this.cantidad = cantidad;
        this.producto = producto;
        this.pedido = pedido;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}
