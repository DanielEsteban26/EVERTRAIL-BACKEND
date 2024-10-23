package pe.edu.cibertec.evertrailbackend.entidad;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Indica que esta clase es una entidad de JPA
@Table(name = "carrito_detalles") // Define el nombre de la tabla en la base de datos
@Data // Genera automáticamente getters, setters, toString, hashCode y equals
@NoArgsConstructor // Genera un constructor sin parámetros
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") // Evita problemas de referencias circulares en la serialización
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Ignora propiedades de Hibernate que no son necesarias en la serialización
public class CarritoDetalle {

    @Id // Indica que este campo es la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera automáticamente el valor de la clave primaria
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Relación muchos a uno con Carrito
    @JoinColumn(name = "carrito_id", nullable = false) // Indica la columna que hace referencia al Carrito
    private Carrito carrito;

    @ManyToOne(fetch = FetchType.LAZY) // Relación muchos a uno con Producto
    @JoinColumn(name = "producto_id", nullable = false) // Indica la columna que hace referencia al Producto
    private Producto producto;

    @Column(name = "cantidad", nullable = false) // Cantidad del producto en el carrito
    private Integer cantidad;

    @Column(name = "precio", nullable = false) // Precio del producto en el carrito
    private Double precio;

}
