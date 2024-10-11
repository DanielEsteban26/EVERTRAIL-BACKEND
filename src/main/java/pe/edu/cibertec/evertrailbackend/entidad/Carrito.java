package pe.edu.cibertec.evertrailbackend.entidad;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity // Indica que esta clase es una entidad de JPA
@Table(name = "carritos") // Define el nombre de la tabla en la base de datos
@Data // Genera automáticamente getters, setters, toString, hashCode y equals
@NoArgsConstructor // Genera un constructor sin parámetros
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") // Evita problemas de referencias circulares en la serialización
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Ignora propiedades de Hibernate que no son necesarias en la serialización
public class Carrito {

    @Id // Indica que este campo es la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera automáticamente el valor de la clave primaria
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Relación muchos a uno con Usuario
    @JoinColumn(name = "usuario_id", nullable = false) // Indica la columna que hace referencia al Usuario
    private Usuario usuario;

    @OneToMany(mappedBy = "carrito", fetch = FetchType.LAZY, cascade = CascadeType.ALL) // Relación uno a muchos con CarritoDetalle
    private Set<CarritoDetalle> carritoDetalles; // Conjunto de detalles en el carrito
}
