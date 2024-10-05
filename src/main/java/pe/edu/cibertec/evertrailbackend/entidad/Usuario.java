package pe.edu.cibertec.evertrailbackend.entidad;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Collections;
import java.util.List;
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

    @Column(name = "contrasenia", nullable = false)
    private String contrasenia; // Contraseña del usuario


    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    @JsonBackReference("rol-usuario") // Rompe la recursión en la serialización
    private Role rol; // Rol del usuario


    @OneToMany(mappedBy = "usuario")
    @JsonBackReference("usuario-pedido") // Rompe la recursión en la serialización
    private Set<Pedido> pedidos; // Conjunto de pedidos realizados por el usuario

    @OneToMany(mappedBy = "usuario")
    @JsonManagedReference("usuario-direccionEnvio") // Rompe la recursión en la serialización
    private Set<DireccionEnvio> direccionesEnvio; // Conjunto de direcciones de envío del usuario

    @OneToMany(mappedBy = "usuario")
    @JsonManagedReference("usuario-metodoPago") // Breaks the recursion in serialization
    private Set<MetodoPago> metodosPago; // Conjunto de métodos de pago del usuario

    @OneToMany(mappedBy = "usuario")
    @JsonManagedReference("usuario-reseñaProducto") // Breaks the recursion in serialization
    private Set<ResenaProducto> reseñasProductos; // Conjunto de reseñas de productos realizadas por el usuario

    // Constructor vacío
    public Usuario() {
    }

    // Constructor con parámetros
    public Usuario(Set<MetodoPago> metodosPago, Set<ResenaProducto> reseñasProductos, Set<DireccionEnvio> direccionesEnvio, Set<Pedido> pedidos, Long id, Role rol, String contraseña, String correo, String nombreUsuario) {
        this.metodosPago = metodosPago;
        this.reseñasProductos = reseñasProductos;
        this.direccionesEnvio = direccionesEnvio;
        this.pedidos = pedidos;
        this.id = id;
        this.rol = rol;
        this.contrasenia = contraseña;
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

    public Set<ResenaProducto> getReseñasProductos() {
        return reseñasProductos;
    }

    public void setReseñasProductos(Set<ResenaProducto> reseñasProductos) {
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

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contraseña) {
        this.contrasenia = contraseña;
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

    // Method to get roles as a List<String>
    public List<String> getRoles() {
        return Collections.singletonList(rol.getNombre());
    }
}