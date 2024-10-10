package pe.edu.cibertec.evertrailbackend.entidad;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usuarios") // Define la tabla de la base de datos
@Data // Genera getters, setters, toString, hashCode y equals
@NoArgsConstructor // Genera un constructor sin parámetros
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") // Serialización para evitar referencias circulares
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Ignora propiedades de Hibernate que no se necesitan en la serialización
public class Usuario {

    @Id // Indica que este es el identificador único
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera el ID automáticamente
    private Long id;

    @Column(name = "nombre_usuario", unique = true, nullable = false) // Campo para el nombre de usuario
    private String nombreUsuario;

    @Column(name = "correo", unique = true, nullable = false) // Campo para el correo electrónico
    private String correo;

    @Column(name = "contrasenia", nullable = false) // Campo para la contraseña
    private String contrasenia;

    @ManyToOne(fetch = FetchType.LAZY) // Relación muchos a uno con Role
    @JoinColumn(name = "rol_id", nullable = false) // Clave foránea
    private Role rol;

    // Relación uno a muchos con Carrito
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Carrito> carritos;

    // Relación uno a muchos con Pedido
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Pedido> pedidos;

    // Relación uno a muchos con DireccionEnvio
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<DireccionEnvio> direccionesEnvio;

    // Relación uno a muchos con MetodoPago
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<MetodoPago> metodosPago;

    // Relación uno a muchos con ResenaProducto
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<ResenaProducto> reseñasProductos;

    // Método para obtener los roles del usuario
    public List<String> getRoles() {
        return Collections.singletonList(rol.getNombre());
    }
}
