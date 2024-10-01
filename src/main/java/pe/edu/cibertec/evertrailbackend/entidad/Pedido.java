package pe.edu.cibertec.evertrailbackend.entidad;

import jakarta.persistence.*;

import java.util.Set;

/**
 * Entidad que representa la tabla de pedidos.
 */

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único para cada pedido

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario; // Usuario que realizó el pedido

    @Column(name = "precio_total", nullable = false)
    private Double precioTotal; // Precio total del pedido

    @Column(name = "estado", nullable = false)
    private String estado = "Pendiente"; // Estado del pedido

    @OneToMany(mappedBy = "pedido")
    private Set<DetallePedido> detallesPedido; // Conjunto de detalles del pedido

    // Constructor vacío
    public Pedido() {
    }

    // Constructor con parámetros
    public Pedido(Long id, Usuario usuario, Double precioTotal, String estado, Set<DetallePedido> detallesPedido) {
        this.id = id;
        this.usuario = usuario;
        this.precioTotal = precioTotal;
        this.estado = estado;
        this.detallesPedido = detallesPedido;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Set<DetallePedido> getDetallesPedido() {
        return detallesPedido;
    }

    public void setDetallesPedido(Set<DetallePedido> detallesPedido) {
        this.detallesPedido = detallesPedido;
    }
}

