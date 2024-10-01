package pe.edu.cibertec.evertrailbackend.entidad;

import jakarta.persistence.*;

import java.util.Set;

/**
 * Entidad que representa la tabla de usuarios.
 */

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único para cada usuario

    @Column(name = "nombre_usuario", unique = true ,nullable = false)
    private String nombreUsuario; // Nombre de usuario único

    @Column(name = "correo", unique = true, nullable = false)
    private String correo; // Correo electrónico único

    @Column(name = "contraseña", nullable = false)
    private String contraseña; // Contraseña del usuario

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
    private Set<ReseñaProducto> reseñasProductos; // Conjunto de reseñas de productos realizadas por el usuario

    // Constructor vacío
    public Usuario() {
    }

    // Constructor con parámetros
    public Usuario(Set<MetodoPago> metodosPago, Set<ReseñaProducto> reseñasProductos, Set<DireccionEnvio> direccionesEnvio, Set<Pedido> pedidos, Long id, Role rol, String contraseña, String correo, String nombreUsuario) {
        this.metodosPago = metodosPago;
        this.reseñasProductos = reseñasProductos;
        this.direccionesEnvio = direccionesEnvio;
        this.pedidos = pedidos;
        this.id = id;
        this.rol = rol;
        this.contraseña = contraseña;
        this.correo = correo;
        this.nombreUsuario = nombreUsuario;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<ReseñaProducto> getReseñasProductos() {
        return reseñasProductos;
    }

    public void setReseñasProductos(Set<ReseñaProducto> reseñasProductos) {
        this.reseñasProductos = reseñasProductos;
    }

    public Set<MetodoPago> getMetodosPago() {
        return metodosPago;
    }

    public void setMetodosPago(Set<MetodoPago> metodosPago) {
        this.metodosPago = metodosPago;
    }

    public Set<DireccionEnvio> getDireccionesEnvio() {
        return direccionesEnvio;
    }

    public void setDireccionesEnvio(Set<DireccionEnvio> direccionesEnvio) {
        this.direccionesEnvio = direccionesEnvio;
    }

    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public Role getRol() {
        return rol;
    }

    public void setRol(Role rol) {
        this.rol = rol;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
