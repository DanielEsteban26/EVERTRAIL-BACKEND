package pe.edu.cibertec.evertrailbackend.entidad;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity // Indica que esta clase es una entidad de JPA
@Table(name = "pedidos") // Define el nombre de la tabla en la base de datos
@Data // Genera automáticamente getters, setters, toString, hashCode y equals
@NoArgsConstructor // Genera un constructor sin parámetros
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") // Evita problemas de referencias circulares en la serialización
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Ignora propiedades de Hibernate que no son necesarias en la serialización
public class Pedido {

    @Id // Indica que este campo es la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera automáticamente el valor de la clave primaria
    private Long id;

    // Relación muchos a uno con Usuario
    @ManyToOne(fetch = FetchType.LAZY) // Carga perezosa para evitar cargar usuarios innecesariamente
    @JoinColumn(name = "usuario_id", nullable = false) // Relación con el usuario asociado al pedido
    private Usuario usuario;

    @Column(name = "precio_total", nullable = false) // Campo para el precio total del pedido
    private Double precioTotal;

    @Column(name = "estado", nullable = false) // Campo para el estado del pedido, por defecto es "Pendiente"
    private String estado = "Pendiente";

    // Relación uno a muchos con DetallePedido
    @OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY, cascade = CascadeType.ALL) // Carga perezosa y cascada para todas las operaciones
    private Set<DetallePedido> detallesPedido; // Conjunto de detalles del pedido
}
