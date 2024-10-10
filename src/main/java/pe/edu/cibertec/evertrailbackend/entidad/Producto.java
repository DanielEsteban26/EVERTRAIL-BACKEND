package pe.edu.cibertec.evertrailbackend.entidad;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity // Indica que esta clase es una entidad de JPA
@Table(name = "productos") // Define el nombre de la tabla en la base de datos
@Data // Genera automáticamente getters, setters, toString, hashCode y equals
@NoArgsConstructor // Genera un constructor sin parámetros
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") // Evita problemas de referencias circulares en la serialización
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Ignora propiedades de Hibernate que no son necesarias en la serialización
public class Producto {

    @Id // Indica que este campo es la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera automáticamente el valor de la clave primaria
    private Long id;

    @Column(name = "nombre", nullable = false) // Nombre del producto, no puede ser nulo
    private String nombre;

    @Column(name = "descripcion") // Descripción del producto
    private String descripcion;

    @Column(name = "precio", nullable = false) // Precio del producto, no puede ser nulo
    private Double precio;

    @Column(name = "stock", nullable = false) // Cantidad en stock del producto, no puede ser nulo
    private Integer stock;

    // Relación muchos a uno con Categoria
    @ManyToOne(fetch = FetchType.LAZY) // Carga perezosa para evitar cargar categorías innecesariamente
    @JoinColumn(name = "categoria_id") // Relación con la categoría asociada
    private Categoria categoria;

    // Relación uno a muchos con ImagenProducto
    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY, cascade = CascadeType.ALL) // Relación con las imágenes del producto
    private Set<ImagenProducto> imagenesProductos;

    // Relación uno a muchos con DetallePedido
    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY, cascade = CascadeType.ALL) // Relación con los detalles de los pedidos
    private Set<DetallePedido> detallesPedido;

    // Relación uno a muchos con ResenaProducto
    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY, cascade = CascadeType.ALL) // Relación con las reseñas del producto
    private Set<ResenaProducto> reseñasProductos;

    // Relación uno a muchos con CarritoDetalle
    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY, cascade = CascadeType.ALL) // Relación con los detalles del carrito
    private Set<CarritoDetalle> carritoDetalles; // Nueva conexión con CarritoDetalle
}
